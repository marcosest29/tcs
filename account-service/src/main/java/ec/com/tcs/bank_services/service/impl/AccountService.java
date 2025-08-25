package ec.com.tcs.bank_services.service.impl;

import ec.com.tcs.bank_services.dto.ValidationResult;
import ec.com.tcs.bank_services.dto.event.ClientEvent;
import ec.com.tcs.bank_services.dto.request.AccountRequest;
import ec.com.tcs.bank_services.dto.request.AccountUpdateRequest;
import ec.com.tcs.bank_services.dto.response.AccountResponse;
import ec.com.tcs.bank_services.exception.AccountException;
import ec.com.tcs.bank_services.mapper.AccountMapper;
import ec.com.tcs.bank_services.model.AccountEntity;
import ec.com.tcs.bank_services.repository.IAccountRepository;
import ec.com.tcs.bank_services.service.IAccountService;
import jakarta.transaction.Transactional;
import lombok.Data;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class AccountService implements IAccountService {

    @Autowired
    private IAccountRepository accountRepository;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.template.exchange:client.exchange}")
    private String exchange;

    private final Map<String, ValidationResult> validationResults = new ConcurrentHashMap<>();

    @Override
    public AccountResponse createAccount(AccountRequest account) {
        AccountEntity accountExists = accountRepository.findByAccountNumber(account.accountNumber());
        if(!ObjectUtils.isEmpty(accountExists)){
            throw new AccountException("Ya existe una cuenta con el numero: "+account.accountNumber());
        }

        return createAccountWithAsyncValidation(account);
    }

    @Override
    public AccountEntity getAccount(String accountNumber) {
        return accountRepository.findByAccountNumberAndStatusTrue(accountNumber);
    }

    @Override
    public AccountResponse updateAccount(String accountNumber, AccountUpdateRequest account) {
        AccountEntity accountExists = accountRepository.findByAccountNumber(accountNumber);
        if(ObjectUtils.isEmpty(accountExists)){
            throw new AccountException("La cuenta no existe");
        }
        accountRepository.updateAccount(accountNumber,account.type(),account.initialBalance());
        return new AccountResponse(accountNumber,accountExists.getClientId(),account.type(),account.initialBalance());
    }

    @Override
    public Boolean deleteAccount(String accountNumber) {
        AccountEntity accountExists = accountRepository.findByAccountNumber(accountNumber);
        if(ObjectUtils.isEmpty(accountExists)){
            throw new AccountException("La cuenta no existe");
        }
        accountRepository.deleteAccount(accountNumber);
        return true;
    }

    public AccountResponse createAccountWithAsyncValidation(AccountRequest account) {
        String validationId = java.util.UUID.randomUUID().toString();
        CountDownLatch latch = new CountDownLatch(1);

        validationResults.put(validationId, new ValidationResult(latch));

        try {
            ClientEvent request = new ClientEvent();
            request.setEventId(validationId);
            request.setEventType("CUSTOMER_VALIDATION_REQUEST");
            request.setClientId(account.clientId());

            rabbitTemplate.convertAndSend(exchange, "client.validation.request", request);

            boolean received = latch.await(10, TimeUnit.SECONDS);

            if (!received) {
                throw new AccountException("Timeout validando cliente");
            }

            ValidationResult result = validationResults.get(validationId);
            if (!result.isValid()) {
                throw new AccountException("Cliente no válido: " + result.getMessage());
            }

            AccountEntity accountEntity = accountMapper.toEntity(account);
            return accountMapper.toResponse(accountRepository.save(accountEntity));

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new AccountException("Validación interrumpida");
        } finally {
            validationResults.remove(validationId);
        }
    }

    @Override
    public ValidationResult getValidationResult(String eventId) {
        return validationResults.get(eventId);
    }
}

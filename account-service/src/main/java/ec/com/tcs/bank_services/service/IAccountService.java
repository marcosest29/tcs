package ec.com.tcs.bank_services.service;

import ec.com.tcs.bank_services.dto.ValidationResult;
import ec.com.tcs.bank_services.dto.request.AccountRequest;
import ec.com.tcs.bank_services.dto.request.AccountUpdateRequest;
import ec.com.tcs.bank_services.dto.response.AccountResponse;
import ec.com.tcs.bank_services.model.AccountEntity;

public interface IAccountService {

    AccountResponse createAccount(AccountRequest account);

    AccountEntity getAccount(String accountNumber);

    AccountResponse updateAccount(String accountNumber, AccountUpdateRequest account);

    Boolean deleteAccount(String accountNumber);

    ValidationResult getValidationResult(String eventId);
}

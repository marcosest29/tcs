package ec.com.tcs.bank_services.service.impl;

import ec.com.tcs.bank_services.dto.request.MovementRequest;
import ec.com.tcs.bank_services.dto.response.IMovementReportProjection;
import ec.com.tcs.bank_services.dto.response.MovementReportResponse;
import ec.com.tcs.bank_services.dto.response.MovementResponse;
import ec.com.tcs.bank_services.exception.AccountException;
import ec.com.tcs.bank_services.exception.MovementException;
import ec.com.tcs.bank_services.mapper.MovementMapper;
import ec.com.tcs.bank_services.model.AccountEntity;
import ec.com.tcs.bank_services.model.MovementEntity;
import ec.com.tcs.bank_services.repository.IAccountRepository;
import ec.com.tcs.bank_services.repository.IMovementRepository;
import ec.com.tcs.bank_services.service.IMovementService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class MovementService implements IMovementService {

    @Autowired
    private IMovementRepository movementRepository;

    @Autowired
    private IAccountRepository accountRepository;

    @Autowired
    private MovementMapper movementMapper;

    @Override
    public MovementResponse createMovement(MovementRequest movement) {
        AccountEntity account = accountRepository.findByAccountNumberAndStatusTrue(movement.accountId());
        if(ObjectUtils.isEmpty(account)){
            throw new MovementException("Numero de cuenta no encontrado: "+movement.accountId());
        }
        if (movement.type().equals("RETIRO") && movement.value()<0){
            if (validateBalance(account.getInitialBalance(),movement.value())){
                throw new MovementException("El saldo es insuficiente para realizar el movimiento.");
            }
        }
        Double finalBalance = account.getInitialBalance()+movement.value();
        accountRepository.updateBalanceAccount(account.getAccountNumber(),finalBalance);
        MovementEntity movementEntity = movementMapper.toEntity(movement);
        movementEntity.setBalance(finalBalance);
        movementEntity.setAccount(account);
        return movementMapper.toResponse(movementRepository.save(movementEntity));
    }

    @Override
    public MovementEntity getMovement(Long id) {
        return movementRepository.findByIdAndStatusTrue(id);
    }

    @Override
    public MovementResponse updateMovement(Long id, MovementRequest movement) {
        MovementEntity movementExists = movementRepository.findByIdAndStatusTrue(id);
        if(ObjectUtils.isEmpty(movementExists)){
            throw new AccountException("El movimiento no existe");
        }
        if (movement.type().equals("RETIRO") && movement.value()<0){
            if (validateBalance(movementExists.getAccount().getInitialBalance()-movementExists.getValue(),movement.value())){
                throw new MovementException("El saldo es insuficiente para realizar la actualizacion del movimiento.");
            }
        }
        Double finalBalance = movementExists.getAccount().getInitialBalance()-movementExists.getValue()+movement.value();
        accountRepository.updateBalanceAccount(movementExists.getAccount().getAccountNumber(),finalBalance);
        movementExists.setBalance(finalBalance);
        movementExists.setValue(movement.value());
        return movementMapper.toResponse(movementRepository.save(movementExists));
    }

    @Override
    public Boolean deleteMovement(Long id) {
        MovementEntity movementExists = movementRepository.findByIdAndStatusTrue(id);
        if(ObjectUtils.isEmpty(movementExists)){
            throw new AccountException("El movimiento no existe");
        }
        Double finalBalance = movementExists.getAccount().getInitialBalance()-movementExists.getValue();
        accountRepository.updateBalanceAccount(movementExists.getAccount().getAccountNumber(),finalBalance);
        movementExists.setBalance(finalBalance);
        movementExists.setStatus(false);
        movementRepository.save(movementExists);
        return true;
    }

    @Override
    public List<MovementReportResponse> generateReport(LocalDate initialDate, LocalDate endDate, String identification) {
        LocalDateTime initial = initialDate.atStartOfDay();
        LocalDateTime end = endDate.atTime(23, 59, 59);
        List<IMovementReportProjection> projections =
                movementRepository.findDataReport(initial, end, identification);
        return projections.stream()
                .map(p -> new MovementReportResponse(
                        p.getDate(),
                        p.getName(),
                        p.getAccountNumber(),
                        p.getType(),
                        p.getInitialBalance(),
                        p.getStatus(),
                        p.getMovement(),
                        p.getBalance()
                ))
                .toList();
    }

    Boolean validateBalance(Double balance, Double value){
        return balance<Math.abs(value);
    }
}

package ec.com.tcs.bank_services.repository;

import ec.com.tcs.bank_services.model.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IAccountRepository extends JpaRepository<AccountEntity,Long> {

    AccountEntity findByAccountNumber(String accountNumber);

    AccountEntity findByAccountNumberAndStatusTrue(String id);

    @Modifying
    @Query("UPDATE AccountEntity a SET a.type = :type, a.initialBalance = :initialBalance WHERE a.accountNumber = :accountNumber")
    void updateAccount(
            @Param("accountNumber")String accountNumber,
            @Param("type") String type,
            @Param("initialBalance") Double initialBalance);

    @Modifying
    @Query("UPDATE AccountEntity a SET a.status = false WHERE a.accountNumber = :accountNumber")
    void deleteAccount(@Param("accountNumber") String accountNumber);

    @Modifying
    @Query("UPDATE AccountEntity a SET a.initialBalance = :initialBalance WHERE a.accountNumber = :accountNumber")
    void updateBalanceAccount(
            @Param("accountNumber")String accountNumber,
            @Param("initialBalance") Double initialBalance);
}

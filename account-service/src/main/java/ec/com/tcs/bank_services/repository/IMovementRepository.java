package ec.com.tcs.bank_services.repository;

import ec.com.tcs.bank_services.dto.response.IMovementReportProjection;
import ec.com.tcs.bank_services.dto.response.MovementReportResponse;
import ec.com.tcs.bank_services.model.MovementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface IMovementRepository extends JpaRepository<MovementEntity,Long> {

    MovementEntity findByIdAndStatusTrue(Long id);

    @Query(value = "SELECT m.date as date, c.name as name, a.account_number as accountNumber, a.type, " +
            "(m.balance - m.value) as initialBalance, a.status, m.value as movement, m.balance as balance " +
            "FROM movements m " +
            "JOIN accounts a ON m.account_number = a.account_number " +
            "JOIN clients c ON c.client_id=a.client_id "+
            "WHERE m.date BETWEEN :initialDate AND :endDate " +
            "AND c.identification = :identification " +
            "AND m.status = true " +
            "ORDER BY m.date DESC",
            nativeQuery = true)
    List<IMovementReportProjection> findDataReport(
            @Param("initialDate") LocalDateTime initialDate,
            @Param("endDate")LocalDateTime endDate,
            @Param("identification")String identification);
}

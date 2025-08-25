package ec.com.tcs.bank_services.dto.response;

import java.time.LocalDateTime;

public interface IMovementReportProjection {
    LocalDateTime getDate();
    String getName();
    String getAccountNumber();
    String getType();
    Double getInitialBalance();
    Boolean getStatus();
    Double getMovement();
    Double getBalance();
}

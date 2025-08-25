package ec.com.tcs.bank_services.dto.response;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class MovementReportResponse {
    LocalDateTime date;
    String client;
    String accountNumber;
    String type;
    Double initialBalance;
    Boolean status;
    Double movement;
    Double balance;

    public MovementReportResponse(LocalDateTime date, String client, String accountNumber, String type, Double initialBalance, Boolean status, Double movement,Double balance) {
        this.date = date;
        this.client = client;
        this.accountNumber = accountNumber;
        this.type = type;
        this.initialBalance = initialBalance;
        this.status = status;
        this.movement = movement;
        this.balance = balance;

    }
}

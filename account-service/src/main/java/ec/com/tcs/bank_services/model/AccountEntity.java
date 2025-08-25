package ec.com.tcs.bank_services.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "accounts")
@Data
public class AccountEntity {

    @Id
    private String accountNumber;

    private String type;

    private Double initialBalance;

    private Boolean status = true;

    private Long clientId;
}

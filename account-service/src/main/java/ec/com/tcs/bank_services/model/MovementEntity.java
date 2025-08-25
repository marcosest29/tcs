package ec.com.tcs.bank_services.model;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "movements")
@Data
public class MovementEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime date;

    private String type;

    private Double value;

    private Double balance;

    @ManyToOne
    @JoinColumn(name = "accountNumber", nullable = false)
    private AccountEntity account;

    private Boolean status;
}

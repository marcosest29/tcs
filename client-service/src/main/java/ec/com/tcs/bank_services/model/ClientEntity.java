package ec.com.tcs.bank_services.model;

import jakarta.persistence.*;
import lombok.Data;
@Data
@Entity
@Table(name = "clients")
public class ClientEntity extends PersonEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clientId;

    @Column(nullable = false)
    private String password;

    private Boolean status = true;
}

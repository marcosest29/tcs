package ec.com.tcs.bank_services.model;

import jakarta.persistence.*;
import lombok.Data;
@MappedSuperclass
@Data
public class PersonEntity {

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 20)
    private String gender;

    private Integer age;

    @Column(unique = true, nullable = false, length = 20)
    private String identification;

    @Column(length = 200)
    private String address;

    @Column(length = 20)
    private String phone;
}

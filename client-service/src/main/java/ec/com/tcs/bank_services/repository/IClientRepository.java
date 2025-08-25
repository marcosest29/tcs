package ec.com.tcs.bank_services.repository;

import ec.com.tcs.bank_services.model.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IClientRepository extends JpaRepository<ClientEntity,Long> {

    ClientEntity findByClientId(Long clientId);

    ClientEntity findByClientIdAndStatusTrue(Long clientId);

    ClientEntity findByIdentification(String identification);

    @Modifying
    @Query("UPDATE ClientEntity c SET c.name = :name, c.gender = :gender, c.age = :age, " +
            "c.identification = :identification, c.address = :address, c.phone = :phone, " +
            "c.password = :password WHERE c.clientId = :clientId")
    void updateClient(
            @Param("clientId") Long clientId,
            @Param("name") String name,
            @Param("gender") String gender,
            @Param("age") Integer age,
            @Param("identification") String identification,
            @Param("address") String address,
            @Param("phone") String phone,
            @Param("password") String password
    );

    @Modifying
    @Query("UPDATE ClientEntity c SET c.status = false WHERE c.clientId = :clientId")
    void deleteClient(@Param("clientId") Long clientId);

    @Query("SELECT c FROM ClientEntity c WHERE c.clientId = :id AND c.identification = :identification")
    ClientEntity findByIdAndIdentification(@Param("id") Long id, @Param("identification") String identification);

}

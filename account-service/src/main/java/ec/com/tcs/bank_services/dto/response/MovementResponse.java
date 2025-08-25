package ec.com.tcs.bank_services.dto.response;

import java.time.LocalDateTime;

public record MovementResponse (
        LocalDateTime date,

        String type,

        Double value,

        Double balance
){
}

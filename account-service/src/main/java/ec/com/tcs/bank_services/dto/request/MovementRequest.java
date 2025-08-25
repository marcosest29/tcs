package ec.com.tcs.bank_services.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record MovementRequest (
        @NotEmpty
        @Pattern(regexp = "^(DEPOSITO|RETIRO)$")
        String type,
        @NotNull
        Double value,
        @NotNull
        String accountId
){
}

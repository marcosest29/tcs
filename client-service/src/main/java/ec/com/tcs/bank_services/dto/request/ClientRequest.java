package ec.com.tcs.bank_services.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ClientRequest(
        @NotEmpty
        String name,
        @NotEmpty
        String gender,
        @NotNull
        @Positive
        Integer age,
        @NotEmpty
        String identification,
        @NotEmpty
        String address,
        @NotEmpty
        String phone,
        @NotEmpty
        String password
) {
}

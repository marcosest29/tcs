package ec.com.tcs.bank_services.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record AccountUpdateRequest(
        @NotEmpty
        String type,
        @NotNull
        @PositiveOrZero
        Double initialBalance
) {
}

package ec.com.tcs.bank_services.dto.response;

public record AccountResponse(
        String accountNumber,
        Long clientId,
        String type,
        Double initialBalance
) {
}

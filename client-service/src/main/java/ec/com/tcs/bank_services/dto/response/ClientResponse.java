package ec.com.tcs.bank_services.dto.response;

public record ClientResponse(
        Long clientId,
        String name,
        String identification
) {
}

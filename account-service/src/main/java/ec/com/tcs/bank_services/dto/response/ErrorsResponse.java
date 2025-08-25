package ec.com.tcs.bank_services.dto.response;

import java.time.LocalDateTime;
import java.util.Map;

public record ErrorsResponse(
        LocalDateTime timestamp,
        int status,
        String error,
        Map<String, String> messages,
        String path
) {
}

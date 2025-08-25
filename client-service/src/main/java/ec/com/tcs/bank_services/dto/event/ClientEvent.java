package ec.com.tcs.bank_services.dto.event;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ClientEvent {
    private String eventId;
    private String eventType;
    private Long clientId;
    private Boolean isValid;
    private String message;
    private LocalDateTime timestamp;
}

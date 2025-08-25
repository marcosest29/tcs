package ec.com.tcs.bank_services.dto;

import lombok.Data;

import java.util.concurrent.CountDownLatch;

@Data
public class ValidationResult {

    private final CountDownLatch latch;
    private boolean isValid;
    private String message;
}

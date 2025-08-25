package ec.com.tcs.bank_services.exception;

public class AccountException extends RuntimeException {
    public AccountException(String message) {
        super(message);
    }
}

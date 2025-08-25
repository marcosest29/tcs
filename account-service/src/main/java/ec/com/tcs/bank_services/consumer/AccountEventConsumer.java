package ec.com.tcs.bank_services.consumer;

import ec.com.tcs.bank_services.dto.ValidationResult;
import ec.com.tcs.bank_services.dto.event.ClientEvent;
import ec.com.tcs.bank_services.service.IAccountService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountEventConsumer {

    @Autowired
    private IAccountService accountService;

    @RabbitListener(queues = "client.validation.response.queue")
    public void handleValidationResponse(ClientEvent event) {
        ValidationResult result = accountService.getValidationResult(event.getEventId());
        if (result != null) {
            result.setValid(event.getIsValid());
            result.setMessage(event.getMessage());
            result.getLatch().countDown();
        }
    }
}
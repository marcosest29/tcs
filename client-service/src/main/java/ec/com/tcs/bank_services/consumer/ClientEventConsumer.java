package ec.com.tcs.bank_services.consumer;

import ec.com.tcs.bank_services.dto.event.ClientEvent;
import ec.com.tcs.bank_services.service.IClientService;
import ec.com.tcs.bank_services.service.rabbit.ClientEventPublisher;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClientEventConsumer {
    @Autowired
    private IClientService clientService;

    @Autowired
    private ClientEventPublisher eventPublisher;

    @RabbitListener(queues = "client.validation.request.queue")
    public void handleValidationRequest(ClientEvent event) {
        try {
            boolean clientExists = clientService.clientExists(event.getClientId());

            ClientEvent response = new ClientEvent();
            response.setEventId(event.getEventId());
            response.setEventType("CLIENT_VALIDATION_RESPONSE");
            response.setClientId(event.getClientId());
            response.setIsValid(clientExists);
            response.setMessage(clientExists ? "Cliente existe" : "Cliente no existe");

            eventPublisher.publishValidationResponse(response);

        } catch (Exception e) {
            ClientEvent errorResponse = new ClientEvent();
            errorResponse.setEventId(event.getEventId());
            errorResponse.setEventType("CLIENT_VALIDATION_RESPONSE");
            errorResponse.setIsValid(false);
            errorResponse.setMessage("Error validando cliente: " + e.getMessage());
            eventPublisher.publishValidationResponse(errorResponse);
        }
    }
}

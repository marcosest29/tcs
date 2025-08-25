package ec.com.tcs.bank_services.service.rabbit;

import ec.com.tcs.bank_services.dto.event.ClientEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ClientEventPublisher {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.template.exchange:client.exchange}")
    private String exchange;

    public void publishValidationResponse(ClientEvent event) {
        rabbitTemplate.convertAndSend(exchange, "client.validation.response", event);
    }
}

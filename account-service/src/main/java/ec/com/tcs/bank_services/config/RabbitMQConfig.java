package ec.com.tcs.bank_services.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public TopicExchange clientExchange() {
        return new TopicExchange("client.exchange");
    }

    @Bean
    public Queue validationRequestQueue() {
        return new Queue("client.validation.request.queue", true);
    }

    @Bean
    public Queue validationResponseQueue() {
        return new Queue("client.validation.response.queue", true);
    }

    @Bean
    public Binding bindingValidationRequest() {
        return BindingBuilder.bind(validationRequestQueue())
                .to(clientExchange())
                .with("client.validation.request");
    }

    @Bean
    public Binding bindingValidationResponse() {
        return BindingBuilder.bind(validationResponseQueue())
                .to(clientExchange())
                .with("client.validation.response");
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}

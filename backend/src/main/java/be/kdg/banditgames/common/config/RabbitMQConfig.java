package be.kdg.banditgames.common.config;

import be.kdg.banditgames.common.events.connect4.GameCreatedEvent;
import be.kdg.banditgames.common.events.connect4.MoveMadeEvent;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class RabbitMQConfig {

    @Bean
    SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory(
            ConnectionFactory connectionFactory
    ) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(jackson2JsonMessageConverter());
        return factory;
    }

    @Bean
    MessageConverter jackson2JsonMessageConverter() {
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();

        DefaultClassMapper classMapper = new DefaultClassMapper();
        classMapper.setTrustedPackages(
                "*"
        );

        // Map incoming RabbitMQ class names to DTOs
        classMapper.setIdClassMapping(Map.of(
                "connect4.domain.event.GameCreatedEvent", GameCreatedEvent.class,
                "connect4.domain.event.MoveMadeEvent", MoveMadeEvent.class
        ));

        converter.setClassMapper(classMapper);
        return converter;
    }
}

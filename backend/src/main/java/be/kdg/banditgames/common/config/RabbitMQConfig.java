package be.kdg.banditgames.common.config;

import be.kdg.banditgames.common.events.chess.ChessAchievementAcquiredEvent;
import be.kdg.banditgames.common.events.chess.ChessGameCreatedEvent;
import be.kdg.banditgames.common.events.chess.ChessGameEndedEvent;
import be.kdg.banditgames.common.events.chess.ChessMoveMadeEvent;
import be.kdg.banditgames.common.events.connect4.Connect4GameCreatedEvent;
import be.kdg.banditgames.common.events.connect4.Connect4MoveMadeEvent;
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
        classMapper.setTrustedPackages("*");

        classMapper.setIdClassMapping(Map.ofEntries(
                // Connect4
                Map.entry(
                        "connect4.domain.event.GameCreatedEvent",
                        Connect4GameCreatedEvent.class
                ),
                Map.entry(
                        "connect4.domain.event.MoveMadeEvent",
                        Connect4MoveMadeEvent.class
                ),

                // Chess — keys must match the incoming __TypeId__ from the producer
                Map.entry(
                        "be.kdg.i5.chess.messaging.messages.GameCreatedMessage",
                        ChessGameCreatedEvent.class
                ),
                Map.entry(
                        "be.kdg.i5.chess.messaging.messages.MoveMadeMessage",
                        ChessMoveMadeEvent.class
                ),
                Map.entry(
                        "be.kdg.i5.chess.messaging.messages.GameEndedMessage",
                        ChessGameEndedEvent.class
                ),
                Map.entry(
                        "be.kdg.i5.chess.messaging.messages.AchievementAcquiredMessage",
                        ChessAchievementAcquiredEvent.class
                )
        ));

        converter.setClassMapper(classMapper);
        return converter;
    }

}

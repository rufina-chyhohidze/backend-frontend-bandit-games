package be.kdg.banditgames.common.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQTopology {

    public static final String CONNECT4_EXCHANGE = "connect4.events";

    // Queues
    public static final String CONNECT4_GAME_CREATED_QUEUE = "connect4.game.created";
    public static final String CONNECT4_MOVE_MADE_QUEUE = "connect4.move.made";
    public static final String CONNECT4_GAME_RESULT_QUEUE = "connect4.game.result";

    @Bean
    TopicExchange connect4Exchange() {
        return ExchangeBuilder
                .topicExchange(CONNECT4_EXCHANGE)
                .durable(true)
                .build();
    }

    @Bean
    Queue connect4GameCreatedQueue() {
        return QueueBuilder.durable(CONNECT4_GAME_CREATED_QUEUE).build();
    }

    @Bean
    Queue connect4MoveMadeQueue() {
        return QueueBuilder.durable(CONNECT4_MOVE_MADE_QUEUE).build();
    }

    @Bean
    Queue connect4GameResultQueue() { return QueueBuilder.durable(CONNECT4_GAME_RESULT_QUEUE).build();
    }

    // Bindings
    @Bean
    Binding bindGameCreated() {
        return BindingBuilder
                .bind(connect4GameCreatedQueue())
                .to(connect4Exchange())
                .with("connect4.game.created.*"); // standard naming
    }

    @Bean
    Binding bindMoveMade() {
        return BindingBuilder
                .bind(connect4MoveMadeQueue())
                .to(connect4Exchange())
                .with("connect4.move.made.*");
    }

    @Bean
    Binding bindGameResult() {
        return BindingBuilder
                .bind(connect4GameResultQueue())
                .to(connect4Exchange())
                .with("connect4.game.result.*");
    }
}

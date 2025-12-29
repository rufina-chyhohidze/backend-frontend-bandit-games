package be.kdg.banditgames.chessACL;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChessRabbitMQTopology {

    public static final String CHESS_EXCHANGE = "gameExchange"; // match Chess docs

    // Queues
    public static final String CHESS_GAME_CREATED_QUEUE = "chess.game.created";
    public static final String CHESS_GAME_UPDATED_QUEUE = "chess.game.updated";
    public static final String CHESS_MOVE_MADE_QUEUE = "chess.move.made";
    public static final String CHESS_GAME_ENDED_QUEUE = "chess.game.ended";
    public static final String CHESS_GAME_REGISTERED_QUEUE = "chess.game.registered";
    public static final String CHESS_ACHIEVEMENT_QUEUE = "chess.achievement";

    // Exchange
    @Bean
    TopicExchange chessExchange() {
        return ExchangeBuilder
                .topicExchange(CHESS_EXCHANGE)
                .durable(true)
                .build();
    }

    // Queues
    @Bean
    Queue chessGameCreatedQueue() {
        return QueueBuilder.durable(CHESS_GAME_CREATED_QUEUE).build();
    }

    @Bean
    Queue chessGameUpdatedQueue() {
        return QueueBuilder.durable(CHESS_GAME_UPDATED_QUEUE).build();
    }

    @Bean
    Queue chessMoveMadeQueue() {
        return QueueBuilder.durable(CHESS_MOVE_MADE_QUEUE).build();
    }

    @Bean
    Queue chessGameEndedQueue() {
        return QueueBuilder.durable(CHESS_GAME_ENDED_QUEUE).build();
    }

    @Bean
    Queue chessGameRegisteredQueue() {
        return QueueBuilder.durable(CHESS_GAME_REGISTERED_QUEUE).build();
    }

    @Bean
    Queue chessAchievementQueue() {
        return QueueBuilder.durable(CHESS_ACHIEVEMENT_QUEUE).build();
    }

    // Bindings - exact routing keys from Chess documentation
    @Bean
    Binding bindChessGameCreated() {
        return BindingBuilder
                .bind(chessGameCreatedQueue())
                .to(chessExchange())
                .with("game.created");
    }

    @Bean
    Binding bindChessGameUpdated() {
        return BindingBuilder
                .bind(chessGameUpdatedQueue())
                .to(chessExchange())
                .with("game.player.names.updated");
    }

    @Bean
    Binding bindChessMoveMade() {
        return BindingBuilder
                .bind(chessMoveMadeQueue())
                .to(chessExchange())
                .with("move.made");
    }

    @Bean
    Binding bindChessGameEnded() {
        return BindingBuilder
                .bind(chessGameEndedQueue())
                .to(chessExchange())
                .with("game.ended");
    }

    @Bean
    Binding bindChessGameRegistered() {
        return BindingBuilder
                .bind(chessGameRegisteredQueue())
                .to(chessExchange())
                .with("game.registered");
    }

    @Bean
    Binding bindChessAchievement() {
        return BindingBuilder
                .bind(chessAchievementQueue())
                .to(chessExchange())
                .with("achievement.acquired");
    }
}

package be.kdg.banditgames.chessACL;

import be.kdg.banditgames.common.events.chess.ChessGameCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class 
ChessGameCreatedEventListener {

    private static final Logger log = LoggerFactory.getLogger(ChessGameCreatedEventListener.class);
    private final GameplayEventPublisher publisher;

    public ChessGameCreatedEventListener(GameplayEventPublisher publisher) {
        this.publisher = publisher;
    }

    @RabbitListener(queues = ChessRabbitMQTopology.CHESS_GAME_CREATED_QUEUE)
    public void onGameCreated(ChessGameCreatedEvent event) {
        log.info("ChessGameCreatedEvent received: {}", event);
        publisher.publishGameCreated(
                event.sessionId()
        );
    }
}

package be.kdg.banditgames.chessACL;

import be.kdg.banditgames.common.events.chess.ChessGameEndedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ChessGameEndedEventListener {

    private static final Logger log = LoggerFactory.getLogger(ChessGameEndedEventListener.class);
    private final GameplayEventPublisher publisher;

    public ChessGameEndedEventListener(GameplayEventPublisher publisher) {
        this.publisher = publisher;
    }

    @RabbitListener(queues = ChessRabbitMQTopology.CHESS_GAME_ENDED_QUEUE)
    public void onGameEnded(ChessGameEndedEvent event) {
        log.info("ChessGameEndedEvent received: {}", event);

        String gameResult = event.winner() == null
                ? "DRAW"
                : event.winner().toUpperCase() + "_WIN";

        publisher.publishGameResult(
                event.sessionId(),
                gameResult
        );
    }

}

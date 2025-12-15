package be.kdg.banditgames.chessACL;

import be.kdg.banditgames.common.events.chess.ChessMoveMadeEvent;
import be.kdg.banditgames.common.shared.PlayerSide;
import be.kdg.banditgames.common.shared.PlayerType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ChessMoveMadeEventListener {

    private static final Logger log = LoggerFactory.getLogger(ChessMoveMadeEventListener.class);
    private final GameplayEventPublisher publisher;

    public ChessMoveMadeEventListener(GameplayEventPublisher publisher) {
        this.publisher = publisher;
    }

    @RabbitListener(queues = ChessRabbitMQTopology.CHESS_MOVE_MADE_QUEUE)
    public void onMoveMade(ChessMoveMadeEvent event) {
        log.info("ChessMoveMadeEvent received: {}", event);

        PlayerSide side = event.player().equalsIgnoreCase("WHITE")
                ? PlayerSide.A
                : PlayerSide.B;

        publisher.publishMoveMade(
                UUID.fromString(event.sessionId()),
                PlayerType.HUMAN,
                side,
                event.moveNumber(),
                event.fenAfterMove(),
                "" // chess has no "legal moves list" like connect4
        );
    }

}

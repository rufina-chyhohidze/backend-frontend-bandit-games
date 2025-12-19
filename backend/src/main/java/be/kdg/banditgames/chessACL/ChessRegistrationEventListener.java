package be.kdg.banditgames.chessACL;

import be.kdg.banditgames.common.events.chess.ChessGameRegisteredEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ChessRegistrationEventListener {

    private static final Logger log = LoggerFactory.getLogger(ChessRegistrationEventListener.class);
    private final PlatformGameRegistrationClient client;

    public ChessRegistrationEventListener(PlatformGameRegistrationClient client) {
        this.client = client;
    }

    @RabbitListener(queues = ChessRabbitMQTopology.CHESS_GAME_REGISTERED_QUEUE)
    public void onGameRegistered(ChessGameRegisteredEvent event) {
        log.info("Chess registered event received: {}", event);

        client.registerGame(
                "Chess",
                "Classic chess game",
                "Standard FIDE rules",
                event.pictureUrl(),
                event.frontendUrl(),
                event.availableAchievements()
        );
    }
}

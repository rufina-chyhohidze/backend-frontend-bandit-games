package be.kdg.banditgames.chessACL;

import be.kdg.banditgames.common.events.chess.ChessAchievementAcquiredEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ChessAchievementAcquiredEventListener {

    private static final Logger log =
            LoggerFactory.getLogger(ChessAchievementAcquiredEventListener.class);

    private final GameplayEventPublisher publisher;

    public ChessAchievementAcquiredEventListener(GameplayEventPublisher publisher) {
        this.publisher = publisher;
    }

    @RabbitListener(queues = ChessRabbitMQTopology.CHESS_ACHIEVEMENT_QUEUE)
    public void onAchievementAcquired(ChessAchievementAcquiredEvent event) {
        log.info("ChessAchievementAcquiredEvent received: {}", event);

        publisher.publishAchievementAcquired(
                event.playerId().toString(),
                event.achievementType()
        );
    }
}

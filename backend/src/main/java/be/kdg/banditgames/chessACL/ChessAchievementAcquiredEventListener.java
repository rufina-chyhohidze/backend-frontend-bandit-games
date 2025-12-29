package be.kdg.banditgames.chessACL;

import be.kdg.banditgames.common.events.chess.ChessAchievementAcquiredEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ChessAchievementAcquiredEventListener {

    private static final Logger log =
            LoggerFactory.getLogger(ChessAchievementAcquiredEventListener.class);

    private final PlatformEventPublisher publisher;

    // to match achievement type with ids
    Map<String, String> ACHIEVEMENT_MAP = Map.ofEntries(
            Map.entry("FIRST_BLOOD", "a1a9ae16-5f9a-46e0-b5ac-804a99c951b8"),
            Map.entry("PAWN_POWER", "c1c39f19-4873-4446-8fbb-129fc9c49dd0"),
            Map.entry("SPEEDY_VICTORY", "d91f47de-4b7b-4269-b567-95c22a78c96a"),
            Map.entry("SPEED_DEMON", "2f7f0a1a-34f8-4d92-bd2f-a93881eb5e2b"),
            Map.entry("WINNER_WINNER_CHICKEN_DINNER", "9c9374b9-1f3a-4c72-a0c9-00019c5d21ff"),
            Map.entry("CASTLE_TIME", "85b147c4-d914-4182-addc-7a57f55d47fb"),
            Map.entry("ROOKIE_MOVE", "c0588f61-8917-43ab-9179-8f6cf8f29d49"),
            Map.entry("PAWN_STORM", "75afcdf1-c478-4844-9e11-ee360a543741")
    );

    public ChessAchievementAcquiredEventListener(PlatformEventPublisher publisher) {
        this.publisher = publisher;
    }

    @RabbitListener(queues = ChessRabbitMQTopology.CHESS_ACHIEVEMENT_QUEUE)
    public void onAchievementAcquired(ChessAchievementAcquiredEvent event) {
        log.info("ChessAchievementAcquiredEvent received: {}", event);

        String achievementType = event.achievementType();
        String achievementId = ACHIEVEMENT_MAP.get(achievementType);

        if (achievementId == null) {
            log.warn("Unknown achievement type received: {}", achievementType);
            return;
        }

        publisher.publishAchievementAcquired(
                event.playerId().toString(),
                achievementId
        );
    }
}

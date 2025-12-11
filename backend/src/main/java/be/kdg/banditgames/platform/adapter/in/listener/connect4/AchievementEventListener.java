package be.kdg.banditgames.platform.adapter.in.listener.connect4;

import be.kdg.banditgames.common.config.RabbitMQTopology;
import be.kdg.banditgames.common.events.connect4.AchievementEvent;
import be.kdg.banditgames.common.shared.AchievementId;
import be.kdg.banditgames.common.shared.PlayerId;
import be.kdg.banditgames.platform.port.in.achievement.AwardAchievementCommand;
import be.kdg.banditgames.platform.port.in.achievement.AwardAchievementUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AchievementEventListener {
    private static final Logger log = LoggerFactory.getLogger(AchievementEventListener.class);

    private final AwardAchievementUseCase awardAchievementUseCase;

    public AchievementEventListener(AwardAchievementUseCase awardAchievementUseCase) {
        this.awardAchievementUseCase = awardAchievementUseCase;
    }

    @RabbitListener(queues = RabbitMQTopology.CONNECT4_ACHIEVEMENT_QUEUE)
    public void achievementEvent(AchievementEvent event) {
        log.info("Received AchievementEvent: {}", event);

        var command = new AwardAchievementCommand(
                PlayerId.of(UUID.fromString(event.playerId())),
                AchievementId.of(UUID.fromString(event.achievementId()))
        );

        awardAchievementUseCase.awardAchievement(command);
    }
}

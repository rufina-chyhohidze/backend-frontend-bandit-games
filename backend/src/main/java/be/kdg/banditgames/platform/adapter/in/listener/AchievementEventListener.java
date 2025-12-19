package be.kdg.banditgames.platform.adapter.in.listener;

import be.kdg.banditgames.platform.config.RabbitMQPlatformTopology;
import be.kdg.banditgames.common.events.generic.GenericAchievementEvent;
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

    @RabbitListener(queues = RabbitMQPlatformTopology.ACHIEVEMENT_UNLOCKED_QUEUE)
    public void achievementEvent(GenericAchievementEvent event) {
        log.info("Received AchievementEvent: {}", event);

        var command = new AwardAchievementCommand(
                PlayerId.of(UUID.fromString(event.playerId())),
                AchievementId.of(UUID.fromString(event.achievementId()))
        );

        awardAchievementUseCase.awardAchievement(command);
    }
}

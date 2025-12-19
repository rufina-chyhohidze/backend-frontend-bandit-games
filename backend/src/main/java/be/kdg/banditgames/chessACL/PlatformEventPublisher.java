package be.kdg.banditgames.chessACL;

import be.kdg.banditgames.common.events.generic.GenericAchievementEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class PlatformEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public PlatformEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishAchievementAcquired(String playerId, String achievementId) {
//        rabbitTemplate.convertAndSend(
//                "platform.events",
//                "achievement.unlocked.chess",
//                new GenericAchievementEvent(playerId, achievementId)
//        );
    }
}

package be.kdg.banditgames.platform.adapter.in.listener.connect4;

import be.kdg.banditgames.common.config.RabbitMQTopology;
import be.kdg.banditgames.common.events.connect4.AchievementEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class AchievementEventListener {
    Logger log = LoggerFactory.getLogger(AchievementEventListener.class);
    // insert port here

    @RabbitListener(queues = RabbitMQTopology.CONNECT4_ACHIEVEMENT_QUEUE)
    public void achievementEvent(AchievementEvent event) {
        log.info("Received AchievementEvent: {}", event);
    }
}

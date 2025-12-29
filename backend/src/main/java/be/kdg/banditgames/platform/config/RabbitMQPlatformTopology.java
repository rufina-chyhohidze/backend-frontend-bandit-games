package be.kdg.banditgames.platform.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQPlatformTopology {

    // Exchange
    public static final String PLATFORM_EXCHANGE = "platform.events";

    // Queues
    public static final String ACHIEVEMENT_UNLOCKED_QUEUE =
            "platform.achievement.unlocked";

    // Routing keys
    public static final String ACHIEVEMENT_UNLOCKED_ROUTING_KEY =
            "achievement.unlocked.*";

    @Bean
    TopicExchange platformExchange() {
        return ExchangeBuilder
                .topicExchange(PLATFORM_EXCHANGE)
                .durable(true)
                .build();
    }

    @Bean
    Queue achievementUnlockedQueue() {
        return QueueBuilder
                .durable(ACHIEVEMENT_UNLOCKED_QUEUE)
                .build();
    }

    @Bean
    Binding bindAchievementUnlocked() {
        return BindingBuilder
                .bind(achievementUnlockedQueue())
                .to(platformExchange())
                .with(ACHIEVEMENT_UNLOCKED_ROUTING_KEY);
    }
}

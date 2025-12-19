package be.kdg.banditgames.chessACL;

import be.kdg.banditgames.common.events.generic.GenericAchievementDto;
import be.kdg.banditgames.common.events.generic.GenericGameRegisteredEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class PlatformEventPublisher {

    private final ApplicationEventPublisher publisher;

    public PlatformEventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    public void publishGameRegistered(UUID registrationId, String frontendUrl, String pictureUrl, List<GenericAchievementDto> availableAchievements) {

        publisher.publishEvent(new GenericGameRegisteredEvent(
                registrationId,
                frontendUrl,
                pictureUrl,
                availableAchievements
        ));
    }
}



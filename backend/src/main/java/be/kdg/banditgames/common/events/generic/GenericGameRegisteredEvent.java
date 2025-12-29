package be.kdg.banditgames.common.events.generic;

import be.kdg.banditgames.common.events.DomainEvent;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record GenericGameRegisteredEvent(
        UUID eventId,
        LocalDateTime occurredAt,
        UUID registrationId,
        String frontendUrl,
        String pictureUrl,
        List<GenericAchievementDto> availableAchievements

) implements DomainEvent {

    @JsonCreator
    public GenericGameRegisteredEvent(
            @JsonProperty("registrationId") UUID registrationId,
            @JsonProperty("frontendUrl") String frontendUrl,
            @JsonProperty("pictureUrl") String pictureUrl,
            @JsonProperty("availableAchievements") List<GenericAchievementDto> availableAchievements
    ) {
        this(UUID.randomUUID(), LocalDateTime.now(),  registrationId, frontendUrl,  pictureUrl, availableAchievements);
    }

    @Override
    public LocalDateTime eventPit() {
        return occurredAt;
    }
}

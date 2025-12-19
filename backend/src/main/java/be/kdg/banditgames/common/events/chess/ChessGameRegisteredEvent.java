package be.kdg.banditgames.common.events.chess;

import be.kdg.banditgames.common.events.DomainEvent;
import be.kdg.banditgames.common.events.generic.GenericAchievementDto;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ChessGameRegisteredEvent(
        UUID eventId,
        LocalDateTime occurredAt,
        UUID registrationId,
        String frontendUrl,
        String pictureUrl,
        List<GenericAchievementDto> availableAchievements,
        String messageType

) implements DomainEvent {

    @JsonCreator
    public ChessGameRegisteredEvent(
            @JsonProperty("registrationId") UUID registrationId,
            @JsonProperty("frontendUrl") String frontendUrl,
            @JsonProperty("pictureUrl") String pictureUrl,
            @JsonProperty("availableAchievements") List<GenericAchievementDto> availableAchievements,
            @JsonProperty("messageType") String messageType,
            @JsonProperty("timestamp") LocalDateTime occurredAt
    ) {
        this(UUID.randomUUID(), occurredAt,  registrationId, frontendUrl,  pictureUrl, availableAchievements, messageType);
    }


    @Override
    public LocalDateTime eventPit() {
        return occurredAt;
    }
}

package be.kdg.banditgames.common.events.generic;

import be.kdg.banditgames.common.events.DomainEvent;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.modulith.events.Externalized;

import java.time.LocalDateTime;
import java.util.UUID;

public record GenericAchievementEvent(
        UUID eventId,
        LocalDateTime occurredAt,
        String playerId,
        String achievementId

) implements DomainEvent {

    @JsonCreator
    public GenericAchievementEvent(
            @JsonProperty("playerId") String playerId,
            @JsonProperty("achievementId") String achievementId
    ) {
        this(UUID.randomUUID(), LocalDateTime.now(), playerId, achievementId);
    }


    @Override
    public LocalDateTime eventPit() {
        return occurredAt;
    }
}

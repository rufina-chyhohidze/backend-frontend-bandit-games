package be.kdg.banditgames.common.events.connect4;

import be.kdg.banditgames.common.events.DomainEvent;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.UUID;

public record Connect4GameResultEvent(
        UUID eventId,
        LocalDateTime occurredAt,
        String sessionId,
        String gameResult
) implements DomainEvent {

    @JsonCreator
    public Connect4GameResultEvent(
            @JsonProperty("sessionId") String sessionId,
            @JsonProperty("gameResult") String gameResult
    ) {
        this(UUID.randomUUID(), LocalDateTime.now(), sessionId, gameResult);
    }

    @Override
    public LocalDateTime eventPit() {
        return null;
    }
}

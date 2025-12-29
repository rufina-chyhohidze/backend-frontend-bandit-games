package be.kdg.banditgames.common.events.generic;

import be.kdg.banditgames.common.events.DomainEvent;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.modulith.events.Externalized;

import java.time.LocalDateTime;
import java.util.UUID;

public record GenericGameResultEvent(
        UUID eventId,
        LocalDateTime occurredAt,
        String sessionId,
        String gameResult
) implements DomainEvent {

    @JsonCreator
    public GenericGameResultEvent(
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

package be.kdg.banditgames.common.events.generic;

import be.kdg.banditgames.common.events.DomainEvent;
import be.kdg.banditgames.common.shared.PlayerType;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.modulith.events.Externalized;

import java.time.LocalDateTime;
import java.util.UUID;

public record GenericGameCreatedEvent(
        UUID eventId,
        LocalDateTime occurredAt,
        String sessionId,
        PlayerType player1,
        PlayerType player2
) implements DomainEvent {

    @JsonCreator
    public GenericGameCreatedEvent(
            @JsonProperty("sessionId") String sessionId,
            @JsonProperty("player1") PlayerType player1,
            @JsonProperty("player2") PlayerType player2
    ) {
        this(UUID.randomUUID(), LocalDateTime.now(), sessionId, player1, player2);
    }


    
    @Override
    public LocalDateTime eventPit() {
        return occurredAt;
    }
}

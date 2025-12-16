package be.kdg.banditgames.common.events.connect4;

import be.kdg.banditgames.common.events.DomainEvent;
import be.kdg.banditgames.common.shared.PlayerType;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.modulith.events.Externalized;

import java.time.LocalDateTime;
import java.util.UUID;

@Externalized("connect4.events::#{'connect4.game.created.' + #this.sessionId()}")
public record Connect4GameCreatedEvent(
        UUID eventId,
        LocalDateTime occurredAt,
        String sessionId,
        String gameId,
        PlayerType player1,
        PlayerType player2
) implements DomainEvent {

    @JsonCreator
    public Connect4GameCreatedEvent(
            @JsonProperty("sessionId") String sessionId,
            @JsonProperty("gameId") String gameId,
            @JsonProperty("player1") PlayerType player1,
            @JsonProperty("player2") PlayerType player2
    ) {
        this(UUID.randomUUID(), LocalDateTime.now(), sessionId, gameId, player1, player2);
    }


    
    @Override
    public LocalDateTime eventPit() {
        return occurredAt;
    }
}

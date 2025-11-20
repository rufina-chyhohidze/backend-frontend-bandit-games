package be.kdg.banditgames.common.events.gameplay;

import be.kdg.banditgames.common.events.DomainEvent;
import org.springframework.modulith.events.Externalized;

import java.time.LocalDateTime;
import java.util.UUID;

@Externalized
public record GameCreatedEvent(
        UUID eventId,
        LocalDateTime occurredAt,
        UUID gameId,
        UUID sessionId,
        PlayerType player1,
        PlayerType player2
) implements DomainEvent {

    public GameCreatedEvent(UUID gameId,
                            UUID sessionId,
                            PlayerType player1,
                            PlayerType player2){
        this(UUID.randomUUID(),
                LocalDateTime.now(),
                gameId,
                sessionId,
                player1,
                player2);
    }
    
    @Override
    public LocalDateTime eventPit() {
        return occurredAt;
    }
}

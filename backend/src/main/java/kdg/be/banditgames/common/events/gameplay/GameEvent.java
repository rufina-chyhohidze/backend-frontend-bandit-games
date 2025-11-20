package kdg.be.banditgames.common.events.gameplay;

import kdg.be.banditgames.common.events.DomainEvent;

import java.time.LocalDateTime;
import java.util.UUID;

public record GameEvent (
        LocalDateTime eventPit,
        UUID eventId,    
        String gameId,
        String sessionId,
        String playerId, 
        String type,
        Object payload,  
        int schemaVersion
) implements DomainEvent {
    
    public GameEvent (UUID eventId,
                       String gameId,
                       String sessionId,
                       String playerId,
                       String type,
                       Object payload,
                       int schemaVersion
    ){
        this(LocalDateTime.now(),
                eventId,
                gameId,
                sessionId,
                playerId,
                type,
                payload,
                schemaVersion);
    }

    @Override
    public LocalDateTime eventPit() {
        return eventPit;
    }
    
}

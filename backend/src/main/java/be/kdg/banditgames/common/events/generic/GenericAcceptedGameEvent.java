package be.kdg.banditgames.common.events.generic;
import be.kdg.banditgames.common.events.DomainEvent;
import java.time.LocalDateTime;
import java.util.UUID;

public record GenericAcceptedGameEvent (    UUID gameId,
                                            String name,
                                            LocalDateTime occurredAt
                                            )
        implements DomainEvent
{

    public GenericAcceptedGameEvent(UUID gameId, String name){
        this(gameId, name, LocalDateTime.now());
    }

    @Override
    public LocalDateTime eventPit() {
        return occurredAt;
    }
}

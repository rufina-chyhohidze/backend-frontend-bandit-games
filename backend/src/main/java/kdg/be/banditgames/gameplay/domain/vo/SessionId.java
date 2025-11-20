package kdg.be.banditgames.gameplay.domain.vo;

import java.util.UUID;

public record SessionId(
        UUID sessionsId
) {
    public static SessionId create() {
        return new SessionId(UUID.randomUUID());
    }
    
    public static SessionId of(UUID sessionsId) {
        return new SessionId(sessionsId);
    }
}

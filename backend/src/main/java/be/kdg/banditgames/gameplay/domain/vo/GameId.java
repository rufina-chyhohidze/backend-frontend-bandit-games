package be.kdg.banditgames.gameplay.domain.vo;

import java.util.UUID;

public record GameId(
        UUID gameId
) {
    public static GameId create() {
        return new GameId(UUID.randomUUID());
    }
    
    public static GameId of(UUID gameId) {
        return new GameId(gameId);
    }
}

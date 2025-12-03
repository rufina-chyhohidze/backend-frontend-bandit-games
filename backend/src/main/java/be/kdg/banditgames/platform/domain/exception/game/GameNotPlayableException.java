package be.kdg.banditgames.platform.domain.exception.game;

import java.util.UUID;

public class GameNotPlayableException extends RuntimeException {
    public GameNotPlayableException(UUID id) {
        super("Game is not playable: " + id);
    }
}

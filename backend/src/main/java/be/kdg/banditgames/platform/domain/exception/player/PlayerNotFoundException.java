package be.kdg.banditgames.platform.domain.exception.player;

import java.util.UUID;

public class PlayerNotFoundException extends RuntimeException {
    public PlayerNotFoundException(UUID id) {
        super("Player not found: " + id);
    }
}

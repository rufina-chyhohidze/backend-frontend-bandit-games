package be.kdg.banditgames.platform.port.in.player;

import java.util.UUID;

public record CreatePlayerCommand(
        UUID playerId,
        String username
) {
}

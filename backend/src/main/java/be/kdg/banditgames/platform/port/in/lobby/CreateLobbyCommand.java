package be.kdg.banditgames.platform.port.in.lobby;

import java.util.UUID;

public record CreateLobbyCommand(
        UUID playerId
) {
}

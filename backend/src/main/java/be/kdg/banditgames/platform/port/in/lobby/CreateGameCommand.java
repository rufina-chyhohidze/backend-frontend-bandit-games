package be.kdg.banditgames.platform.port.in.lobby;

import be.kdg.banditgames.common.shared.PlayerType;

import java.util.UUID;

public record CreateGameCommand(
        UUID sessionId,
        UUID gameId,
        UUID player1Id,
        UUID player2Id,
        PlayerType player1Type,
        PlayerType player2Type
) {
}

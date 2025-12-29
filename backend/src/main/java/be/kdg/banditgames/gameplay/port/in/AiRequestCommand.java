package be.kdg.banditgames.gameplay.port.in;

import be.kdg.banditgames.common.shared.PlayerType;

import java.util.UUID;

public record AiRequestCommand(
        UUID sessionId,
        int moveNumber,
        PlayerType playerType,
        String gameState,
        String legalMoves
) {
}

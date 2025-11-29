package be.kdg.banditgames.gameplay.port.in;

import java.util.UUID;

public record AiRequestCommand(
        UUID sessionId,
        int moveNumber,
        String gameState,
        String legalMoves
) {
}

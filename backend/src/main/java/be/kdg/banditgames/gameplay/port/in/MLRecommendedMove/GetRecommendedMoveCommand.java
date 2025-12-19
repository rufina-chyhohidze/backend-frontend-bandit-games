package be.kdg.banditgames.gameplay.port.in.MLRecommendedMove;

import java.util.UUID;

public record GetRecommendedMoveCommand(
        UUID sessionId,
        int moveNumber,
        String aiType,
        String gameState,
        String legalMoves
) {
}

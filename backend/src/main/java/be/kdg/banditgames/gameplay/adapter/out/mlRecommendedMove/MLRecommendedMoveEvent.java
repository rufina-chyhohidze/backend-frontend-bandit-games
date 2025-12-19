package be.kdg.banditgames.gameplay.adapter.out.mlRecommendedMove;

import java.util.UUID;

public record MLRecommendedMoveEvent(
        UUID sessionId,
        int moveNumber,
        String aiType,
        String gameState,
        String legalMoves,
        String move,
        double confidenceScore
) {
}

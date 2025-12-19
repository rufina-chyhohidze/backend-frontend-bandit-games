package be.kdg.banditgames.common.events.ml;

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

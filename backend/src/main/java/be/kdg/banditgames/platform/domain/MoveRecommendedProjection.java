package be.kdg.banditgames.platform.domain;

import be.kdg.banditgames.common.shared.PlayerType;
import be.kdg.banditgames.common.shared.SessionId;

public record MoveRecommendedProjection (
         SessionId sessionId,
         int moveNumber,
         PlayerType aiType,
         String gameState,
         String legalMoves,
         String recommendedMove,
         double confidence
) {
}
package be.kdg.banditgames.platform.port.in.ml;

import be.kdg.banditgames.common.shared.PlayerType;
import be.kdg.banditgames.common.shared.SessionId;

import java.util.UUID;

public record CreateMoveRecommendedCommand (
        UUID sessionId,
        int moveNumber,
        String aiType,
        String gameState,
        String legalMoves,
        String recommendedMove,
        double confidence
){
}

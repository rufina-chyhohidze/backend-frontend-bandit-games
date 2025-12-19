package be.kdg.banditgames.platform.domain;

import be.kdg.banditgames.common.shared.PlayerType;
import be.kdg.banditgames.common.shared.SessionId;

import java.util.List;

public record WinProbabilityProjection(
        SessionId sessionId,
        int moveNumber,
        PlayerType aiType,
        String gameState,
        String legalMoves,
        double player1WinProbability,
        double player2WinProbability,
        double activePlayerWinProbability,
        List<Double> distribution
) {
}

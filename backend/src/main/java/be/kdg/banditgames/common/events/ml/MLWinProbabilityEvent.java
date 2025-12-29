package be.kdg.banditgames.common.events.ml;

import java.util.List;
import java.util.UUID;

public record MLWinProbabilityEvent (
        UUID sessionId,
        int moveNumber,
        String aiType,
        String gameState,
        String legalMoves,
        double player1WinProbability,
        double player2WinProbability,
        double activePlayerWinProbability,
        List<Double> distribution
        
) {
}

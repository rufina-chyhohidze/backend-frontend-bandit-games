package be.kdg.banditgames.platform.port.in.ml;

import java.util.List;
import java.util.UUID;

public record CreateWinProbabilityCommand (
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

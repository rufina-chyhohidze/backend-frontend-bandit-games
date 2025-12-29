package be.kdg.banditgames.gameplay.domain;

import java.util.List;

public record WinProbability(
        double player1WinProbability,
        double player2WinProbability,
        double activePlayerWinProbability,
        List<Double> distribution
) {
}

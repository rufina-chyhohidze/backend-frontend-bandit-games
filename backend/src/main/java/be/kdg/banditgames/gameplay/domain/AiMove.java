package be.kdg.banditgames.gameplay.domain;

public record AiMove(
        String move,
        double confidenceScore
) {
}

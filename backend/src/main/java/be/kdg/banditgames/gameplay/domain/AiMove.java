package be.kdg.banditgames.gameplay.domain;

public record AiMove(
        String move,
        double confidenceScore,
        String bestMove,
        Double heuristicScore,
        Integer visitCount,
        Integer searchDepth
) {
}


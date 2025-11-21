package be.kdg.banditgames.gameplay.adapter.out.mlRecommendedMove;

public record MLRecommendedMoveRequest(
        String gameState,
        String legalMoves
) {
}

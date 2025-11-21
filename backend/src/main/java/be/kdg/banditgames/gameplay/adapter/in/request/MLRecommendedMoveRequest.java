package be.kdg.banditgames.gameplay.adapter.in.request;

public record MLRecommendedMoveRequest(
        String gameState,
        String legalMoves
) {
}

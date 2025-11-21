package be.kdg.banditgames.gameplay.adapter.in.request;

public record AiRequest(
        String gameState,
        String legalMoves
) {
}

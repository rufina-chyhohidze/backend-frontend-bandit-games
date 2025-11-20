package be.kdg.banditgames.gameplay.adapter.in.request;

public record AiRequestDto(
        String gameState,
        String legalMoves
) {
}

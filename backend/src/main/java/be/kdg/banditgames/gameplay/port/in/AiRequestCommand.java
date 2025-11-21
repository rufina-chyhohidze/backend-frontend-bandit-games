package be.kdg.banditgames.gameplay.port.in;

public record AiRequestCommand(
        String gameState,
        String legalMoves
) {
}

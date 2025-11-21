package be.kdg.banditgames.gameplay.port.in.MLRecommendedMoveCommand;

public record GetRecommendedMoveCommand(
        String gameState,
        String legalMoves
) {
}

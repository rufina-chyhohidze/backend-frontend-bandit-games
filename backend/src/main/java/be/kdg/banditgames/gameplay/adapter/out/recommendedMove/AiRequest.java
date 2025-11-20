package be.kdg.banditgames.gameplay.adapter.out.recommendedMove;

public record AiRequest (
        String gameState,
        String legalMoves
){
}

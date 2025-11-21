package be.kdg.banditgames.gameplay.adapter.out.aiAgentMove;

public record AiRequestDto(
        String gameState,
        String legalMoves
){
}

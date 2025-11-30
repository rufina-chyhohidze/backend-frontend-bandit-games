package be.kdg.banditgames.gameplay.adapter.out.aiAgentMove;

public record AiRequestDto(
        String sessionId,
        int moveNumber,
        String AiType,
        String gameState,
        String legalMoves
){
}
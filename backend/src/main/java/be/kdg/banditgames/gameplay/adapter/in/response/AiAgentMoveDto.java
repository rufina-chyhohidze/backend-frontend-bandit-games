package be.kdg.banditgames.gameplay.adapter.in.response;

public record AiAgentMoveDto(
    String recommendedMove,
    double confidenceScore,
    String bestMove,
    Double heuristicScore,
    Integer visitCount,
    Integer searchDepth
){
}

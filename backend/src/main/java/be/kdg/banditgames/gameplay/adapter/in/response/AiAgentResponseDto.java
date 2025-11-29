package be.kdg.banditgames.gameplay.adapter.in.response;

import be.kdg.banditgames.gameplay.port.in.AiMoveMetadata;

public record AiAgentResponseDto(
    String recommendedMove,
    double confidenceScore,
    String bestMove,
    Double heuristicScore,
    Integer visitCount,
    Integer searchDepth
){
    public static AiAgentResponseDto fromMetadata(AiMoveMetadata metadata) {
        return new AiAgentResponseDto(
                metadata.recommendedMove(),
                metadata.confidenceScore(),
                metadata.bestMove(),
                metadata.heuristicScore(),
                metadata.visitCount(),
                metadata.searchDepth()
        );
    }
}

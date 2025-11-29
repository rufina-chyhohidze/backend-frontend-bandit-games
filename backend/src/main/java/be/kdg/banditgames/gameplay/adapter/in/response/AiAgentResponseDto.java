package be.kdg.banditgames.gameplay.adapter.in.response;

import be.kdg.banditgames.gameplay.port.in.AiMoveMetadata;

public record AiAgentResponseDto(
    String recommendedMove
){
    public static AiAgentResponseDto fromMetadata(AiMoveMetadata metadata) {
        return new AiAgentResponseDto(
                metadata.recommendedMove()
        );
    }
}

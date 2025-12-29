package be.kdg.banditgames.gameplay.port.in.MLRecommendedMove;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record GetRecommendedMoveCommand(
        UUID sessionId,
        int moveNumber,
        @JsonProperty("AiType") String aiType,
        String gameState,
        String legalMoves
) {
}

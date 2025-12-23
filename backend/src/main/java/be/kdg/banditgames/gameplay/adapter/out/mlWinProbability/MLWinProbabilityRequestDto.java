package be.kdg.banditgames.gameplay.adapter.out.mlWinProbability;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Request DTO for ML win-probability endpoint.
 * Maps Java field names to Python's expected format (AiType with uppercase A).
 */
public record MLWinProbabilityRequestDto(
        String sessionId,
        int moveNumber,
        @JsonProperty("AiType") String aiType,
        String gameState,
        String legalMoves
) {
}
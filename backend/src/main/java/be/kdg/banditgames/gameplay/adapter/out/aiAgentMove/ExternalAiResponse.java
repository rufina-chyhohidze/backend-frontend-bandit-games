package be.kdg.banditgames.gameplay.adapter.out.aiAgentMove;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ExternalAiResponse(
        @JsonProperty("move") String move,
        @JsonProperty("confidence") double confidence,
        @JsonProperty("bestMove") String bestMove,
        @JsonProperty("heuristic") Double heuristic,
        @JsonProperty("visits") Integer visits,
        @JsonProperty("depth") Integer depth,
        @JsonProperty("legalMoves") String legalMoves
    ) {}
package be.kdg.banditgames.gameplay.adapter.out.aiAgentMove;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ExternalAiResponse(
        @JsonProperty("recommendedMove") String move,
        @JsonProperty("confidenceScore") double confidence,
        @JsonProperty("bestMove") String bestMove,
        @JsonProperty("heuristicScore") Double heuristic,
        @JsonProperty("visitCount") Integer visits,
        @JsonProperty("searchDepth") Integer depth
) {}
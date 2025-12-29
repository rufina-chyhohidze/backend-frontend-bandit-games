package be.kdg.banditgames.gameplay.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RecommendedMove(
        @JsonProperty("recommendedMove") String move,

        @JsonProperty("confidence") double confidenceScore
) {
}
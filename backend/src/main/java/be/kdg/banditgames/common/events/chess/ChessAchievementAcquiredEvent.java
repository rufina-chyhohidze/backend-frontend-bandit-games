package be.kdg.banditgames.common.events.chess;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record ChessAchievementAcquiredEvent(
        String sessionId,
        UUID playerId,
        String playerName,
        String achievementType,
        String achievementDescription
) {

    @JsonCreator
    public ChessAchievementAcquiredEvent(
            @JsonProperty("gameId") String sessionId,  // ← Changed from "sessionId" to "gameId"
            @JsonProperty("playerId") UUID playerId,
            @JsonProperty("playerName") String playerName,
            @JsonProperty("achievementType") String achievementType,
            @JsonProperty("achievementDescription") String achievementDescription
    ) {
        this.sessionId = sessionId;
        this.playerId = playerId;
        this.playerName = playerName;
        this.achievementType = achievementType;
        this.achievementDescription = achievementDescription;
    }
}
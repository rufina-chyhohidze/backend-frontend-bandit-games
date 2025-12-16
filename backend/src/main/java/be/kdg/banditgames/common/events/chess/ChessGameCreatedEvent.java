package be.kdg.banditgames.common.events.chess;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public record ChessGameCreatedEvent(
        String sessionId,
        String name,
        String player1,
        String player2,
        String currentFen,
        String status
) {

    @JsonCreator
    public ChessGameCreatedEvent(
            @JsonProperty("gameId") String sessionId,  // ← Changed from "sessionId" to "gameId"
            @JsonProperty("name") String name,
            @JsonProperty("whitePlayerName") String player1,  // ← Changed to "whitePlayerName"
            @JsonProperty("blackPlayerName") String player2,  // ← Changed to "blackPlayerName"
            @JsonProperty("currentFen") String currentFen,
            @JsonProperty("status") String status
    ) {
        this.name = name;
        this.sessionId = sessionId;
        this.player1 = player1;
        this.player2 = player2;
        this.currentFen = currentFen;
        this.status = status;
    }
}
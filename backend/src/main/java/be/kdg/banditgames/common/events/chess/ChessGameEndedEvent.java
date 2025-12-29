package be.kdg.banditgames.common.events.chess;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public record ChessGameEndedEvent(
        String sessionId,
        String whitePlayer,
        String blackPlayer,
        String finalFen,
        String endReason,
        String winner,
        int totalMoves
) {

    @JsonCreator
    public ChessGameEndedEvent(
            @JsonProperty("gameId") String sessionId,  // ← Changed from "sessionId" to "gameId"
            @JsonProperty("whitePlayerName") String whitePlayer,  // ← Changed to "whitePlayerName"
            @JsonProperty("blackPlayerName") String blackPlayer,  // ← Changed to "blackPlayerName"
            @JsonProperty("finalFen") String finalFen,
            @JsonProperty("endReason") String endReason,
            @JsonProperty("winner") String winner,
            @JsonProperty("totalMoves") int totalMoves
    ) {
        this.sessionId = sessionId;
        this.whitePlayer = whitePlayer;
        this.blackPlayer = blackPlayer;
        this.finalFen = finalFen;
        this.endReason = endReason;
        this.winner = winner;
        this.totalMoves = totalMoves;
    }
}
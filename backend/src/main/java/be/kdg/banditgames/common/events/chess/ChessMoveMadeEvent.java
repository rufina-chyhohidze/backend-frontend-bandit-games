package be.kdg.banditgames.common.events.chess;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record ChessMoveMadeEvent(
        String sessionId,
        String player,
        int moveNumber,
        String fromSquare,
        String toSquare,
        String sanNotation,
        String fenAfterMove,
        String whitePlayer,
        String blackPlayer,
        LocalDateTime moveTime
) {

    @JsonCreator
    public ChessMoveMadeEvent(
            @JsonProperty("gameId") String sessionId,  // ← Changed from "sessionId" to "gameId"
            @JsonProperty("player") String player,
            @JsonProperty("moveNumber") int moveNumber,
            @JsonProperty("fromSquare") String fromSquare,
            @JsonProperty("toSquare") String toSquare,
            @JsonProperty("sanNotation") String sanNotation,
            @JsonProperty("fenAfterMove") String fenAfterMove,
            @JsonProperty("whitePlayerName") String whitePlayer,  // ← Changed to "whitePlayerName"
            @JsonProperty("blackPlayerName") String blackPlayer,  // ← Changed to "blackPlayerName"
            @JsonProperty("moveTime") LocalDateTime moveTime
    ) {
        this.sessionId = sessionId;
        this.player = player;
        this.moveNumber = moveNumber;
        this.fromSquare = fromSquare;
        this.toSquare = toSquare;
        this.sanNotation = sanNotation;
        this.fenAfterMove = fenAfterMove;
        this.whitePlayer = whitePlayer;
        this.blackPlayer = blackPlayer;
        this.moveTime = moveTime;
    }
}
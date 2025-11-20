package kdg.be.banditgames.gameplay.domain;

import kdg.be.banditgames.gameplay.domain.vo.SessionId;

import java.time.LocalDateTime;

public class GameState {
    SessionId sessionId;
    LocalDateTime timestamp;
    PlayerType playerType;
    PlayerSide playerSide;
    int moveNumber;
    String board;
    String legalMoves;

    public GameState(SessionId sessionId, PlayerType playerType, PlayerSide playerSide, int moveNumber, String board, String legalMoves) {
        this.sessionId = sessionId;
        this.timestamp = LocalDateTime.now();
        this.playerType = playerType;
        this.playerSide = playerSide;
        this.moveNumber = moveNumber;
        this.board = board;
        this.legalMoves = legalMoves;
    }
    
}

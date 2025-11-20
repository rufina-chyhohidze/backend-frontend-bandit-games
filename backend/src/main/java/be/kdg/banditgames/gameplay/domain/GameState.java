package be.kdg.banditgames.gameplay.domain;

import be.kdg.banditgames.common.events.DomainEvent;
import be.kdg.banditgames.common.events.gameplay.PlayerSide;
import be.kdg.banditgames.common.events.gameplay.PlayerType;
import be.kdg.banditgames.gameplay.domain.vo.SessionId;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GameState {
    SessionId sessionId;
    LocalDateTime timestamp;
    PlayerType playerType;
    PlayerSide playerSide;
    int moveNumber;
    String board;
    String legalMoves;
    
    List<DomainEvent> domainEvents = new ArrayList<>();

    private GameState(SessionId sessionId, PlayerType playerType, PlayerSide playerSide, int moveNumber, String board, String legalMoves) {
        this.sessionId = sessionId;
        this.timestamp = LocalDateTime.now();
        this.playerType = playerType;
        this.playerSide = playerSide;
        this.moveNumber = moveNumber;
        this.board = board;
        this.legalMoves = legalMoves;
    }
    
    public static GameState createNew(SessionId sessionId, PlayerType playerType, PlayerSide playerSide, int moveNumber, String board, String legalMoves) {
        return new GameState(sessionId, playerType, playerSide, moveNumber, board, legalMoves);
    }

    public List<DomainEvent> getDomainEvents() {
        return domainEvents;
    }

    public void addDomainEvents(DomainEvent domainEvent) {
        this.domainEvents.add(domainEvent);
    }

    public void clearDomainEvents() {
        this.domainEvents.clear();
    }

    public SessionId getSessionId() {
        return sessionId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public PlayerType getPlayerType() {
        return playerType;
    }

    public PlayerSide getPlayerSide() {
        return playerSide;
    }

    public int getMoveNumber() {
        return moveNumber;
    }

    public String getBoard() {
        return board;
    }

    public String getLegalMoves() {
        return legalMoves;
    }
}

package be.kdg.banditgames.gameplay.domain;

import be.kdg.banditgames.common.events.DomainEvent;
import be.kdg.banditgames.common.events.gameplay.PlayerSide;
import be.kdg.banditgames.common.events.gameplay.PlayerType;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GameState {
    private final LocalDateTime timestamp;
    private final PlayerType playerType;
    private final PlayerSide playerSide;
    private final int moveNumber;
    private final String board;
    private final String legalMoves;

    private final List<DomainEvent> domainEvents = new ArrayList<>();

    private GameState(PlayerType playerType,
                      PlayerSide playerSide,
                      int moveNumber,
                      String board,
                      String legalMoves) {
        this.timestamp = LocalDateTime.now();
        this.playerType = playerType;
        this.playerSide = playerSide;
        this.moveNumber = moveNumber;
        this.board = board;
        this.legalMoves = legalMoves;
    }

    public static GameState createNew(PlayerType playerType,
                                      PlayerSide playerSide,
                                      int moveNumber,
                                      String board,
                                      String legalMoves) {
        return new GameState(playerType, playerSide, moveNumber, board, legalMoves);
    }

    public List<DomainEvent> getDomainEvents() { return domainEvents; }
    public void addDomainEvent(DomainEvent domainEvent) { this.domainEvents.add(domainEvent); }
    public void clearDomainEvents() { this.domainEvents.clear(); }

    public LocalDateTime getTimestamp() { return timestamp; }
    public PlayerType getPlayerType() { return playerType; }
    public PlayerSide getPlayerSide() { return playerSide; }
    public int getMoveNumber() { return moveNumber; }
    public String getBoard() { return board; }
    public String getLegalMoves() { return legalMoves; }
}

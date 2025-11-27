package be.kdg.banditgames.gameplay.domain;

import be.kdg.banditgames.common.events.DomainEvent;
import be.kdg.banditgames.common.shared.PlayerSide;
import be.kdg.banditgames.common.shared.PlayerType;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GameState {
    private LocalDateTime timestamp;
    private PlayerType playerType;
    private PlayerSide playerSide;
    private int moveNumber;
    private String board;
    private String legalMoves;
    private String bestMove;
    private Double winProbability; //comes from the AI (MCTS)
    private Double confidenceScore;

    List<DomainEvent> domainEvents = new ArrayList<>();

    private GameState(PlayerType playerType, PlayerSide playerSide, int moveNumber, String board, String legalMoves) {
        this.timestamp = LocalDateTime.now();
        this.playerType = playerType;
        this.playerSide = playerSide;
        this.moveNumber = moveNumber;
        this.board = board;
        this.legalMoves = legalMoves;
    }
    
    public static GameState createNew(PlayerType playerType, PlayerSide playerSide, int moveNumber, String board, String legalMoves) {
        return new GameState(playerType, playerSide, moveNumber, board, legalMoves);
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

    public String getBestMove() {
        return bestMove;
    }

    public Double getWinProbability() {
        return winProbability;
    }

    public Double getConfidenceScore() {
        return confidenceScore;
    }

    // TODO: Check if: Adding Double winProbabililty in AIMove and save it in GameState or just add this seperate
    public void addAIFeatures( String bestMove, Double winProbability, Double confidenceScore){
        this.bestMove = bestMove;
        this.winProbability = winProbability;
        this.confidenceScore = confidenceScore;
    }
}

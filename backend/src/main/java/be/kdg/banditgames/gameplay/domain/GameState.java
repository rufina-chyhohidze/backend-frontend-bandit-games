package be.kdg.banditgames.gameplay.domain;

import be.kdg.banditgames.common.events.DomainEvent;
import be.kdg.banditgames.common.shared.PlayerSide;
import be.kdg.banditgames.common.shared.PlayerType;
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

    private final Integer actualMove;
    private final Integer aiHardRecommendedMove;
    private final Double aiHardConfidence;
    private final Double aiHardWinProbability;
    private final Integer aiMlRecommendedMove;
    private final Double aiMlConfidence;
    private final Double aiMlWinProbability;

    private final List<DomainEvent> domainEvents = new ArrayList<>();

    private GameState(PlayerType playerType,
                      PlayerSide playerSide,
                      int moveNumber,
                      String board,
                      String legalMoves,
                      Integer actualMove,
                      Integer aiHardRecommendedMove,
                      Double aiHardConfidence,
                      Double aiHardWinProbability,
                      Integer aiMlRecommendedMove,
                      Double aiMlConfidence,
                      Double aiMlWinProbability) {
        this.timestamp = LocalDateTime.now();
        this.playerType = playerType;
        this.playerSide = playerSide;
        this.moveNumber = moveNumber;
        this.board = board;
        this.legalMoves = legalMoves;
        this.actualMove = actualMove;
        this.aiHardRecommendedMove = aiHardRecommendedMove;
        this.aiHardConfidence = aiHardConfidence;
        this.aiHardWinProbability = aiHardWinProbability;
        this.aiMlRecommendedMove = aiMlRecommendedMove;
        this.aiMlConfidence = aiMlConfidence;
        this.aiMlWinProbability = aiMlWinProbability;
    }

    public static GameState createNew(PlayerType playerType,
                                      PlayerSide playerSide,
                                      int moveNumber,
                                      String board, String legalMoves) {
        return new GameState(playerType, playerSide, moveNumber, board, legalMoves, null, null, null, null, null, null, null);
    }

    public static GameState createNewWithAiMetadata(PlayerType playerType,
                                                     PlayerSide playerSide,
                                                     int moveNumber,
                                                     String board,
                                                     String legalMoves,
                                                     Integer actualMove,
                                                     Integer aiHardRecommendedMove,
                                                     Double aiHardConfidence,
                                                     Double aiHardWinProbability,
                                                     Integer aiMlRecommendedMove,
                                                     Double aiMlConfidence,
                                                     Double aiMlWinProbability) {
        return new GameState(playerType, playerSide, moveNumber, board, legalMoves,
                            actualMove, aiHardRecommendedMove, aiHardConfidence, aiHardWinProbability,
                            aiMlRecommendedMove, aiMlConfidence, aiMlWinProbability);
    }

    public List<DomainEvent> getDomainEvents() { return domainEvents; }
    public void addDomainEvent(DomainEvent domainEvent) { this.domainEvents.add(domainEvent); }
    public void clearDomainEvents() { this.domainEvents.clear(); }
    public String getLegalMoves() { return legalMoves; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public PlayerType getPlayerType() { return playerType; }
    public PlayerSide getPlayerSide() { return playerSide; }
    public int getMoveNumber() { return moveNumber; }
    public String getBoard() { return board; }

    public Integer getActualMove() { return actualMove; }
    public Integer getAiHardRecommendedMove() { return aiHardRecommendedMove; }
    public Double getAiHardConfidence() { return aiHardConfidence; }
    public Double getAiHardWinProbability() { return aiHardWinProbability; }
    public Integer getAiMlRecommendedMove() { return aiMlRecommendedMove; }
    public Double getAiMlConfidence() { return aiMlConfidence; }
    public Double getAiMlWinProbability() { return aiMlWinProbability; }
}

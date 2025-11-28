package be.kdg.banditgames.gameplay.adapter.out;

import be.kdg.banditgames.common.events.gameplay.PlayerSide;
import be.kdg.banditgames.common.events.gameplay.PlayerType;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

public class GameStateMongoEmbedded {

    @Field("timestamp")
    private LocalDateTime timestamp;

    @Field("player_type")
    private PlayerType playerType;

    @Field("player_side")
    private PlayerSide playerSide;

    @Field("move_number")
    private int moveNumber;

    @Field("board")
    private String board;

    @Field("legal_moves")
    private String legalMoves;
    // AI/ML annotations (persistence/logging concerns)
    @Field("best_move")
    private String bestMove;

    @Field("confidence_score")
    private Double confidenceScore;

    @Field("heuristic_score")
    private Double heuristicScore;

    @Field("visit_count")
    private Integer visitCount;

    @Field("search_depth")
    private Integer searchDepth;


    public GameStateMongoEmbedded() {}

    public GameStateMongoEmbedded(LocalDateTime timestamp,
                                  PlayerType playerType,
                                  PlayerSide playerSide,
                                  int moveNumber,
                                  String board,
                                  String legalMoves,
                                  String bestMove,
                                  Double confidenceScore,
                                  Double heuristicScore,
                                  Integer visitCount,
                                  Integer searchDepth) {
        this.timestamp = timestamp;
        this.playerType = playerType;
        this.playerSide = playerSide;
        this.moveNumber = moveNumber;
        this.board = board;
        this.legalMoves = legalMoves;
        this.bestMove = bestMove;
        this.confidenceScore = confidenceScore;
        this.heuristicScore = heuristicScore;
        this.visitCount = visitCount;
        this.searchDepth = searchDepth;
    }

    public LocalDateTime getTimestamp() { return timestamp; }
    public PlayerType getPlayerType() { return playerType; }
    public PlayerSide getPlayerSide() { return playerSide; }
    public int getMoveNumber() { return moveNumber; }
    public String getBoard() { return board; }
    public String getLegalMoves() { return legalMoves; }
    public String getBestMove() { return bestMove; }
    public Double getConfidenceScore() { return confidenceScore; }
    public Double getHeuristicScore() { return heuristicScore; }
    public Integer getVisitCount() { return visitCount; }
    public Integer getSearchDepth() { return searchDepth; }
}

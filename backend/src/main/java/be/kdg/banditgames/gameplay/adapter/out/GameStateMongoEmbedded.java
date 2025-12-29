package be.kdg.banditgames.gameplay.adapter.out;

import be.kdg.banditgames.common.shared.PlayerSide;
import be.kdg.banditgames.common.shared.PlayerType;
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
    @Field("ai_metadata_embedded")
    private AiMetadataEmbedded aiMetadataEmbedded;



    public GameStateMongoEmbedded() {}

    public GameStateMongoEmbedded(LocalDateTime timestamp,
                                  PlayerType playerType,
                                  PlayerSide playerSide,
                                  int moveNumber,
                                  String board,
                                  String legalMoves,
                                  AiMetadataEmbedded aiMetadataEmbedded
                                  ) {
        this.timestamp = timestamp;
        this.playerType = playerType;
        this.playerSide = playerSide;
        this.moveNumber = moveNumber;
        this.board = board;
        this.legalMoves = legalMoves;
        this.aiMetadataEmbedded=aiMetadataEmbedded;
    }
    public String getLegalMoves() {
        return legalMoves;
    }
    public LocalDateTime getTimestamp() { return timestamp; }
    public PlayerType getPlayerType() { return playerType; }
    public PlayerSide getPlayerSide() { return playerSide; }
    public int getMoveNumber() { return moveNumber; }
    public String getBoard() { return board; }
    public AiMetadataEmbedded getAiMetadataEmbedded() { return aiMetadataEmbedded; }
}

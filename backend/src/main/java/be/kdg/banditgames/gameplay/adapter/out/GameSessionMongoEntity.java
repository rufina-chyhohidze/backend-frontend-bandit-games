package be.kdg.banditgames.gameplay.adapter.out;

import be.kdg.banditgames.gameplay.domain.GameResult;
import be.kdg.banditgames.gameplay.domain.GameSessionState;
import be.kdg.banditgames.common.shared.PlayerType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Document("game_session")
public class GameSessionMongoEntity {

    @Id
    private UUID sessionId;

    @Indexed
    @Field("game_id")
    private UUID gameId;

    @Field("player_type")
    private PlayerType playerType;

    @Field("player2_type")
    private PlayerType player2Type;

    @Field("session_state")
    private GameSessionState gameSessionState;

    @Indexed
    @Field("start_time")
    private LocalDateTime startTime;

    @Indexed
    @Field("end_time")
    private LocalDateTime endTime;

    @Field("winner_id")
    private GameResult gameResult;

    // Embedded move snapshots with AI annotations
    @Field("game_states")
    private List<GameStateMongoEmbedded> gameStates;

    public GameSessionMongoEntity() {}

    public GameSessionMongoEntity(UUID sessionId,
                                  UUID gameId,
                                  PlayerType playerType,
                                  PlayerType player2Type,
                                  GameSessionState gameSessionState,
                                  LocalDateTime startTime,
                                  LocalDateTime endTime,
                                  GameResult gameResult,
                                  List<GameStateMongoEmbedded> gameStates) {
        this.sessionId = sessionId;
        this.gameId = gameId;
        this.playerType = playerType;
        this.player2Type = player2Type;
        this.gameStates = gameStates;
        this.gameSessionState = gameSessionState;
        this.startTime = startTime;
        this.endTime = endTime;
        this.gameResult = gameResult;
        this.gameStates = gameStates;
    }

    public UUID getSessionId() { return sessionId; }
    public UUID getGameId() { return gameId; }
    public PlayerType getPlayerType() { return playerType; }
    public PlayerType getPlayer2Type() { return player2Type; }
    public GameSessionState getGameSessionState() { return gameSessionState; }
    public LocalDateTime getStartTime() { return startTime; }
    public LocalDateTime getEndTime() { return endTime; }
    public GameResult getGameResult() { return gameResult; }
    public List<GameStateMongoEmbedded> getGameStates() { return gameStates; }
}

package be.kdg.banditgames.gameplay.adapter.out;

import be.kdg.banditgames.gameplay.domain.GameSessionState;
import be.kdg.banditgames.gameplay.domain.GameState;
import be.kdg.banditgames.common.events.gameplay.PlayerSide;
import be.kdg.banditgames.common.events.gameplay.PlayerType;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Document("game_session")
public class GameSessionMongoEntity {

    @Id
    private UUID sessionId;

    @Field("game_id")
    private UUID gameId;

    @Field("player_type")
    private PlayerType playerType;

    @Field("player2_type")
    private PlayerType player2Type;

    @Field("game_states")
    private List<GameState> gameStates;

    @Field("session_state")
    private GameSessionState gameSessionState;

    @Field("start_time")
    private LocalDateTime startTime;

    @Field("end_time")
    private LocalDateTime endTime;

    @Field("winner_id")
    private PlayerSide winnerId;

    public GameSessionMongoEntity(
            UUID sessionId,
            UUID gameId,
            PlayerType playerType,
            PlayerType player2Type,
            List<GameState> gameStates,
            GameSessionState gameSessionState,
            LocalDateTime startTime,
            LocalDateTime endTime,
            PlayerSide winnerId
    ) {
        this.sessionId = sessionId;
        this.gameId = gameId;
        this.playerType = playerType;
        this.player2Type = player2Type;
        this.gameStates = gameStates;
        this.gameSessionState = gameSessionState;
        this.startTime = startTime;
        this.endTime = endTime;
        this.winnerId = winnerId;
    }

    public GameSessionMongoEntity() {}

    public UUID getSessionId() { return sessionId; }
    public UUID getGameId() { return gameId; }
    public PlayerType getPlayerType() { return playerType; }
    public PlayerType getPlayer2Type() { return player2Type; }
    public List<GameState> getGameStates() { return gameStates; }
    public GameSessionState getGameSessionState() { return gameSessionState; }
    public LocalDateTime getStartTime() { return startTime; }
    public LocalDateTime getEndTime() { return endTime; }
    public PlayerSide getWinnerId() { return winnerId; }
}

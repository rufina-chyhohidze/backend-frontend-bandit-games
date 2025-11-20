package kdg.be.banditgames.gameplay.adapter.out;

import kdg.be.banditgames.gameplay.domain.GameSessionState;
import kdg.be.banditgames.gameplay.domain.GameState;
import kdg.be.banditgames.gameplay.domain.PlayerType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

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

    public GameSessionMongoEntity(UUID sessionId, UUID gameId, PlayerType playerType, PlayerType player2Type, List<GameState> gameStates, GameSessionState gameSessionState) {
        this.sessionId = sessionId;
        this.gameId = gameId;
        this.playerType = playerType;
        this.player2Type = player2Type;
        this.gameStates = gameStates;
        this.gameSessionState = gameSessionState;
    }

    public UUID getSessionId() {
        return sessionId;
    }

    public void setSessionId(UUID sessionId) {
        this.sessionId = sessionId;
    }

    public UUID getGameId() {
        return gameId;
    }

    public void setGameId(UUID gameId) {
        this.gameId = gameId;
    }

    public PlayerType getPlayerType() {
        return playerType;
    }

    public void setPlayerType(PlayerType playerType) {
        this.playerType = playerType;
    }

    public PlayerType getPlayer2Type() {
        return player2Type;
    }

    public void setPlayer2Type(PlayerType player2Type) {
        this.player2Type = player2Type;
    }

    public List<GameState> getGameStates() {
        return gameStates;
    }

    public void setGameStates(List<GameState> gameStates) {
        this.gameStates = gameStates;
    }

    public GameSessionState getGameSessionState() {
        return gameSessionState;
    }

    public void setGameSessionState(GameSessionState gameSessionState) {
        this.gameSessionState = gameSessionState;
    }
}
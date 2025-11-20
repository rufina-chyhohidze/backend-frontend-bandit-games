package kdg.be.banditgames.gameplay.domain;

import kdg.be.banditgames.gameplay.domain.vo.GameId;
import kdg.be.banditgames.gameplay.domain.vo.SessionId;

import java.util.List;

public class GameSession {
    
    GameId gameId;
    SessionId sessionId;
    PlayerType playerType; 
    PlayerType player2Type;
    List<GameState> gameStates;
    GameSessionState gameSessionState;

    private GameSession(GameId gameId, SessionId sessionId, PlayerType playerType, PlayerType player2Type, GameSessionState gameSessionState) {
        this.gameId = gameId;
        this.sessionId = sessionId;
        this.playerType = playerType;
        this.player2Type = player2Type;
        this.gameSessionState = gameSessionState;
    }
    
    public static GameSession createNew(GameId gameId, PlayerType playerType, PlayerType player2Type) {
        return new GameSession(gameId, SessionId.create(), playerType, player2Type, GameSessionState.IN_PROGRESS);
    }
    
    public static GameSession rehydrate(GameId gameId, SessionId sessionId, PlayerType playerType, PlayerType player2Type, GameSessionState gameSessionState) {
        return new GameSession(gameId, sessionId, playerType, player2Type, gameSessionState);
    }
    
    public void addGameState(GameState gameState) {
        this.gameStates.add(gameState);
    }
    
    public void finishGame(){
        this.gameSessionState = GameSessionState.COMPLETED;
    }

    public GameId getGameId() {
        return gameId;
    }

    public SessionId getSessionsId() {
        return sessionId;
    }

    public PlayerType getPlayerType() {
        return playerType;
    }

    public PlayerType getPlayer2Type() {
        return player2Type;
    }

    public List<GameState> getGameStates() {
        return gameStates;
    }

    public GameSessionState getGameSessionState() {
        return gameSessionState;
    }
}

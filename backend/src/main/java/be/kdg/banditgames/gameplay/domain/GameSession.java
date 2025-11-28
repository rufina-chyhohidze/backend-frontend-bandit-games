package be.kdg.banditgames.gameplay.domain;

import be.kdg.banditgames.common.shared.PlayerSide;
import be.kdg.banditgames.common.shared.PlayerType;
import be.kdg.banditgames.common.shared.GameId;
import be.kdg.banditgames.common.shared.SessionId;

import java.time.LocalDateTime;
import java.util.List;

public class GameSession {

    private GameId gameId;
    private SessionId sessionId;
    private PlayerType playerType;
    private PlayerType player2Type;
    private List<GameState> gameStates;
    private GameSessionState gameSessionState;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private PlayerSide winnerId;

    private GameSession(GameId gameId, SessionId sessionId, PlayerType playerType, PlayerType player2Type, GameSessionState gameSessionState, LocalDateTime startTime) {
        this.gameId = gameId;
        this.sessionId = sessionId;
        this.playerType = playerType;
        this.player2Type = player2Type;
        this.gameSessionState = gameSessionState;
        this.startTime = startTime;
    }
    
    public static GameSession createNew(GameId gameId, SessionId sessionId, PlayerType playerType, PlayerType player2Type) {
        return new GameSession(gameId, sessionId, playerType, player2Type, GameSessionState.IN_PROGRESS, LocalDateTime.now());
    }
    
    public static GameSession rehydrate(GameId gameId, SessionId sessionId, PlayerType playerType, PlayerType player2Type, GameSessionState gameSessionState, LocalDateTime startTime, LocalDateTime endTime, PlayerSide winnerId) {
        GameSession session =  new GameSession(gameId, sessionId, playerType, player2Type, gameSessionState, startTime);
        session.endTime = endTime;
        session.winnerId = winnerId;
        return session;
    }
    
    public void addGameState(GameState gameState) {
        this.gameStates.add(gameState);
    }
    
    public void finishGame(PlayerSide winnerId){
        this.winnerId = winnerId;
        this.endTime = LocalDateTime.now();
        this.gameSessionState = GameSessionState.COMPLETED;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public PlayerSide getWinnerId() {
        return winnerId;
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

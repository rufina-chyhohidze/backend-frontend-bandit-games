package be.kdg.banditgames.gameplay.domain;

import be.kdg.banditgames.common.events.gameplay.PlayerSide;
import be.kdg.banditgames.common.events.gameplay.PlayerType;
import be.kdg.banditgames.gameplay.domain.vo.GameId;
import be.kdg.banditgames.gameplay.domain.vo.SessionId;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GameSession {

    private final GameId gameId;
    private final SessionId sessionId;
    private final PlayerType playerType;      // consider replacing with PlayerId/V.O. later
    private final PlayerType player2Type;
    private final List<GameState> gameStates = new ArrayList<>();
    private GameSessionState gameSessionState;
    private final LocalDateTime startTime;
    private LocalDateTime endTime;
    private PlayerSide winnerId;

    private GameSession(GameId gameId,
                        SessionId sessionId,
                        PlayerType playerType,
                        PlayerType player2Type,
                        GameSessionState gameSessionState,
                        LocalDateTime startTime) {
        this.gameId = gameId;
        this.sessionId = sessionId;
        this.playerType = playerType;
        this.player2Type = player2Type;
        this.gameSessionState = gameSessionState;
        this.startTime = startTime;
    }

    public static GameSession createNew(GameId gameId,
                                        SessionId sessionId,
                                        PlayerType playerType,
                                        PlayerType player2Type) {
        return new GameSession(gameId, sessionId, playerType, player2Type,
                GameSessionState.IN_PROGRESS, LocalDateTime.now());
    }

    public static GameSession rehydrate(GameId gameId,
                                        SessionId sessionId,
                                        PlayerType playerType,
                                        PlayerType player2Type,
                                        GameSessionState gameSessionState,
                                        LocalDateTime startTime,
                                        LocalDateTime endTime,
                                        PlayerSide winnerId,
                                        List<GameState> states) {
        GameSession session = new GameSession(gameId, sessionId, playerType, player2Type, gameSessionState, startTime);
        session.endTime = endTime;
        session.winnerId = winnerId;
        if (states != null) session.gameStates.addAll(states);
        return session;
    }

    public void addGameState(GameState gameState) {
        if (this.gameSessionState == GameSessionState.COMPLETED) {
            throw new IllegalStateException("Cannot add moves to a completed game");
        }
        this.gameStates.add(gameState);
    }

    public void finishGame(PlayerSide winnerId){
        this.winnerId = winnerId;
        this.endTime = LocalDateTime.now();
        this.gameSessionState = GameSessionState.COMPLETED;
    }
    public PlayerSide getCurrentTurn() {
        int moveCount = gameStates.size();
        return moveCount % 2 == 0 ? PlayerSide.A : PlayerSide.B;
    }

    public boolean isNextPlayerAi() {
        PlayerSide currentTurn = getCurrentTurn();
        return (currentTurn == PlayerSide.A && !(playerType == PlayerType.HUMAN)) ||
                (currentTurn == PlayerSide.B && !(playerType == PlayerType.HUMAN));
    }

    public PlayerType getCurrentPlayerType() {
        PlayerSide currentTurn = getCurrentTurn();
        return currentTurn == PlayerSide.A ? playerType : player2Type;
    }

    public String getCurrentBoardState() {
        if (gameStates.isEmpty()) {
            return getInitialBoard(); // Empty Connect4 board
        }
        return gameStates.get(gameStates.size() - 1).getBoard();
    }
    //TODO: check how to do this adaptable for different games
    private String getInitialBoard() {
        // Empty Connect4 board (6 rows x 7 columns = 42 cells)
        return "o ".repeat(42).trim();
    }

    public LocalDateTime getStartTime() { return startTime; }
    public LocalDateTime getEndTime() { return endTime; }
    public PlayerSide getWinnerId() { return winnerId; }
    public GameId getGameId() { return gameId; }
    public SessionId getSessionsId() { return sessionId; }
    public PlayerType getPlayerType() { return playerType; }
    public PlayerType getPlayer2Type() { return player2Type; }
    public List<GameState> getGameStates() { return gameStates; }
    public GameSessionState getGameSessionState() { return gameSessionState; }
}

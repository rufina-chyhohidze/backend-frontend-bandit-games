package be.kdg.banditgames.gameplay.adapter.out;

import be.kdg.banditgames.gameplay.domain.GameSession;
import be.kdg.banditgames.gameplay.domain.GameState;
import be.kdg.banditgames.gameplay.domain.vo.GameId;
import be.kdg.banditgames.gameplay.domain.vo.SessionId;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GameSessionMongoMapper {

    public static GameSessionMongoEntity fromDomain(GameSession gameSession) {
        UUID sessionId = gameSession.getSessionsId().sessionsId();
        UUID gameId = gameSession.getGameId().gameId();

        List<GameState> gameStates = gameSession.getGameStates() != null ? gameSession.getGameStates() : new ArrayList<>();

        return new GameSessionMongoEntity(
                sessionId,
                gameId,
                gameSession.getPlayerType(),
                gameSession.getPlayer2Type(),
                gameStates,
                gameSession.getGameSessionState()
        );
    }

    public static GameSession toDomain(GameSessionMongoEntity entity) {

        GameId gameId = GameId.of(entity.getGameId());
        SessionId sessionId = SessionId.of(entity.getSessionId());

        GameSession gameSession = GameSession.rehydrate(
                gameId,
                sessionId,
                entity.getPlayerType(),
                entity.getPlayer2Type(),
                entity.getGameSessionState()
        );

        if (entity.getGameStates() != null) {
            entity.getGameStates().forEach(gameSession::addGameState);
        }

        return gameSession;
    }

}
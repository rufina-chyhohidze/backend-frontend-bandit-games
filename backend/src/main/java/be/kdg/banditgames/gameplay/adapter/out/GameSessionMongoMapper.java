package be.kdg.banditgames.gameplay.adapter.out;

import be.kdg.banditgames.gameplay.domain.GameSession;
import be.kdg.banditgames.gameplay.domain.GameState;
import be.kdg.banditgames.gameplay.domain.GameSessionState;
import be.kdg.banditgames.common.events.gameplay.PlayerSide;
import be.kdg.banditgames.common.events.gameplay.PlayerType;
import be.kdg.banditgames.gameplay.domain.vo.GameId;
import be.kdg.banditgames.gameplay.domain.vo.SessionId;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GameSessionMongoMapper {

    public static GameSessionMongoEntity fromDomain(GameSession gameSession) {
        return new GameSessionMongoEntity(
                gameSession.getSessionsId().sessionsId(),
                gameSession.getGameId().gameId(),
                gameSession.getPlayerType(),
                gameSession.getPlayer2Type(),
                gameSession.getGameStates(),
                gameSession.getGameSessionState(),
                gameSession.getStartTime(),
                gameSession.getEndTime(),
                gameSession.getWinnerId()
        );
    }

    public static GameSession toDomain(GameSessionMongoEntity entity) {

        GameSession gameSession = GameSession.rehydrate(
                GameId.of(entity.getGameId()),
                SessionId.of(entity.getSessionId()),
                entity.getPlayerType(),
                entity.getPlayer2Type(),
                entity.getGameSessionState(),
                entity.getStartTime(),
                entity.getEndTime(),
                entity.getWinnerId()
        );

        if (entity.getGameStates() != null) {
            entity.getGameStates().forEach(gameSession::addGameState);
        }

        return gameSession;
    }
}

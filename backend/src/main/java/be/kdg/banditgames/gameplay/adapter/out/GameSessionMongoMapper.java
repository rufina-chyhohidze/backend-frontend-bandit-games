package be.kdg.banditgames.gameplay.adapter.out;
import org.springframework.stereotype.Component;
import be.kdg.banditgames.gameplay.domain.GameSession;
import be.kdg.banditgames.gameplay.domain.GameState;
import be.kdg.banditgames.gameplay.port.in.AiMoveMetadata;
import java.util.List;
import be.kdg.banditgames.common.shared.GameId;
import be.kdg.banditgames.common.shared.SessionId;

@Component
public class GameSessionMongoMapper {

    public static GameSessionMongoEntity fromDomain(GameSession gameSession) {
        return new GameSessionMongoEntity(
                gameSession.getSessionsId().sessionsId(),
                gameSession.getGameId().gameId(),
                gameSession.getPlayerType(),
                gameSession.getPlayer2Type(),
                gameSession.getGameSessionState(),
                gameSession.getStartTime(),
                gameSession.getEndTime(),
                gameSession.getWinnerId(),
                null
        );
    }

    public static GameSessionMongoEntity fromDomain(GameSession gameSession,
                                                    List<GameStateMongoEmbedded> embeddedStates) {
        return new GameSessionMongoEntity(
                gameSession.getSessionsId().sessionsId(),
                gameSession.getGameId().gameId(),
                gameSession.getPlayerType(),
                gameSession.getPlayer2Type(),
                gameSession.getGameSessionState(),
                gameSession.getStartTime(),
                gameSession.getEndTime(),
                gameSession.getWinnerId(),
                embeddedStates
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
                entity.getWinnerId(),
                null
        );

        if (entity.getGameStates() != null) {
            entity.getGameStates().forEach(gs -> {
                GameState domainState = GameState.createNew(
                        gs.getPlayerType(),
                        gs.getPlayerSide(),
                        gs.getMoveNumber(),
                        gs.getBoard(),
                        gs.getLegalMoves());
                gameSession.addGameState(domainState);
            });
        }

        return gameSession;
    }

    // Build a single embedded state from one domain state and optional annotation
    public static GameStateMongoEmbedded toEmbeddedState(GameState state, AiMetadataEmbedded ann) {
        return new GameStateMongoEmbedded(
                state.getTimestamp(),
                state.getPlayerType(),
                state.getPlayerSide(),
                state.getMoveNumber(),
                state.getBoard(),
                state.getLegalMoves(),
                ann
        );
    }
}

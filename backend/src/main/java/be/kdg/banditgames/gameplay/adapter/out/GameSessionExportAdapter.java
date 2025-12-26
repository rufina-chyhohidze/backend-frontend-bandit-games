package be.kdg.banditgames.gameplay.adapter.out;

import be.kdg.banditgames.platform.port.out.GameSessionExportPort;
import be.kdg.banditgames.platform.port.out.dto.AiMetadataExportData;
import be.kdg.banditgames.platform.port.out.dto.GameSessionExportData;
import be.kdg.banditgames.platform.port.out.dto.GameStateExportData;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class GameSessionExportAdapter implements GameSessionExportPort {

    private final MongoGameplayRepository mongoGameplayRepository;

    public GameSessionExportAdapter(MongoGameplayRepository mongoGameplayRepository) {
        this.mongoGameplayRepository = mongoGameplayRepository;
    }

    @Override
    public List<GameSessionExportData> findAllSessionsOrderedByStartTime() {
        return mongoGameplayRepository.findAllByOrderByStartTimeDesc()
                .stream()
                .map(this::toExportData)
                .toList();
    }

    @Override
    public Optional<GameSessionExportData> findSessionById(UUID sessionId) {
        return mongoGameplayRepository.findBySessionId(sessionId)
                .map(this::toExportData);
    }

    private GameSessionExportData toExportData(GameSessionMongoEntity entity) {
        List<GameStateExportData> states = null;
        if (entity.getGameStates() != null) {
            states = entity.getGameStates().stream()
                    .map(this::toStateExportData)
                    .toList();
        }

        return new GameSessionExportData(
                entity.getSessionId(),
                entity.getGameId(),
                entity.getPlayerType() != null ? entity.getPlayerType().name() : null,
                entity.getPlayer2Type() != null ? entity.getPlayer2Type().name() : null,
                entity.getGameSessionState() != null ? entity.getGameSessionState().name() : null,
                entity.getStartTime(),
                entity.getEndTime(),
                entity.getGameResult() != null ? entity.getGameResult().name() : null,
                states
        );
    }

    private GameStateExportData toStateExportData(GameStateMongoEmbedded state) {
        AiMetadataExportData aiMeta = null;
        if (state.getAiMetadataEmbedded() != null) {
            AiMetadataEmbedded embedded = state.getAiMetadataEmbedded();
            aiMeta = new AiMetadataExportData(
                    embedded.getRecommendedMove(),
                    embedded.getConfidenceScore(),
                    embedded.getBestMove(),
                    embedded.getHeuristicScore(),
                    embedded.getVisitCount(),
                    embedded.getSearchDepth()
            );
        }

        return new GameStateExportData(
                state.getTimestamp(),
                state.getPlayerType() != null ? state.getPlayerType().name() : null,
                state.getPlayerSide() != null ? state.getPlayerSide().name() : null,
                state.getMoveNumber(),
                state.getBoard(),
                state.getLegalMoves(),
                aiMeta
        );
    }
}

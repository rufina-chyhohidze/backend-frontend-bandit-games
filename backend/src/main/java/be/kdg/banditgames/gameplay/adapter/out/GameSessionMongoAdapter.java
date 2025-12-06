package be.kdg.banditgames.gameplay.adapter.out;

import be.kdg.banditgames.common.shared.PlayerType;
import be.kdg.banditgames.gameplay.adapter.out.aiMetadataPending.AiMetadataPendingEntity;
import be.kdg.banditgames.gameplay.adapter.out.aiMetadataPending.MongoAiMetadataPendingRepository;
import be.kdg.banditgames.gameplay.domain.GameResult;
import be.kdg.banditgames.gameplay.domain.GameSession;
import be.kdg.banditgames.gameplay.domain.GameState;
import be.kdg.banditgames.common.shared.SessionId;
import be.kdg.banditgames.gameplay.port.in.AiMoveMetadata;
import be.kdg.banditgames.gameplay.port.out.aiMetadataPending.DeleteAiMetadataPendingPort;
import be.kdg.banditgames.gameplay.port.out.aiMetadataPending.LoadAiMetadataPendingPort;
import be.kdg.banditgames.gameplay.port.out.gameSession.LoadGameSessionPort;
import be.kdg.banditgames.gameplay.port.out.gameSession.PersistGameSessionPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public class GameSessionMongoAdapter implements PersistGameSessionPort, LoadGameSessionPort {
    private final LoadAiMetadataPendingPort loadAiPendingPort;
    private final DeleteAiMetadataPendingPort deleteAiPendingPort;
    private final MongoGameplayRepository mongoGameplayRepository;
    private final MongoTemplate mongoTemplate;
    private final Logger logger = LoggerFactory.getLogger(GameSessionMongoAdapter.class);

    public GameSessionMongoAdapter(LoadAiMetadataPendingPort loadAiPendingPort, DeleteAiMetadataPendingPort deleteAiPendingPort, MongoGameplayRepository mongoGameplayRepository,
                                   MongoTemplate mongoTemplate) {
        this.loadAiPendingPort = loadAiPendingPort;
        this.deleteAiPendingPort = deleteAiPendingPort;
        this.mongoTemplate = mongoTemplate;
        this.mongoGameplayRepository = mongoGameplayRepository;
    }
    
    @Override
    public void save(GameSession gameSession) {
        GameSessionMongoEntity entity = GameSessionMongoMapper.fromDomain(gameSession);
        GameSessionMongoEntity savedEntity = mongoGameplayRepository.save(entity);
        logger.info("Saved GameSession with id: {}", savedEntity.getSessionId());
        GameSessionMongoMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<GameSession> loadGameSessionById(SessionId sessionId) {
        Optional<GameSessionMongoEntity> entity = mongoGameplayRepository.findBySessionId(sessionId.sessionsId());
        return entity.map(GameSessionMongoMapper::toDomain);
    }

    @Override
    public void appendMove(SessionId sessionId, GameState gameState) {

        UUID idValue = sessionId.sessionsId();

        boolean exists = mongoGameplayRepository.existsById(idValue);
        logger.info("Exists? {}", exists);

        Query query = new Query(Criteria.where("_id").is(idValue));
        AiMetadataEmbedded aiMetadata = null;

        if (gameState.getPlayerType() != PlayerType.HUMAN) {
            Optional<AiMetadataPendingEntity> pending =
                    loadAiPendingPort.findAndDelete(sessionId.sessionsId(), gameState.getMoveNumber());

            if (pending.isPresent()) {
                aiMetadata = pending.get().getMetadata();
            }
        }
        GameStateMongoEmbedded embedded = GameSessionMongoMapper.toEmbeddedState(gameState, aiMetadata);

        Update update = new Update().push("game_states", embedded);
        var result = mongoTemplate.updateFirst(query, update, GameSessionMongoEntity.class);
        logger.info("Matched: {}, Modified: {}", result.getMatchedCount(), result.getModifiedCount());
    }

    @Override
    public void markCompleted(SessionId id, GameResult result, LocalDateTime endTime) {

        Query query = new Query(Criteria.where("_id").is(id.sessionsId()));

        Update update = new Update()
                .set("game_result", result.name())
                .set("end_time", endTime)
                .set("session_state", "COMPLETED");

        var dbResult = mongoTemplate.updateFirst(query, update, GameSessionMongoEntity.class);

        logger.info("markCompleted: Matched={}, Modified={}",
                dbResult.getMatchedCount(), dbResult.getModifiedCount());
    }
}

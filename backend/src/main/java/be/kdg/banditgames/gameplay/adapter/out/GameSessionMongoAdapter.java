package be.kdg.banditgames.gameplay.adapter.out;

import be.kdg.banditgames.gameplay.adapter.in.response.AiAgentMoveDto;
import be.kdg.banditgames.gameplay.domain.AiMove;
import be.kdg.banditgames.gameplay.domain.GameSession;
import be.kdg.banditgames.gameplay.domain.GameState;
import be.kdg.banditgames.gameplay.domain.vo.SessionId;
import be.kdg.banditgames.gameplay.port.out.gameSession.LoadGameSessionPort;
import be.kdg.banditgames.gameplay.port.out.gameSession.PersistGameSessionPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public class GameSessionMongoAdapter implements PersistGameSessionPort, LoadGameSessionPort {
    private final MongoGameplayRepository mongoGameplayRepository;
    private final MongoTemplate mongoTemplate;
    private final Logger logger = LoggerFactory.getLogger(GameSessionMongoAdapter.class);

    public GameSessionMongoAdapter(MongoGameplayRepository mongoGameplayRepository,
                                   MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
        this.mongoGameplayRepository = mongoGameplayRepository;
    }

    @Override
    public void save(GameSession gameSession) {
        GameSessionMongoEntity entity = GameSessionMongoMapper.fromDomain(gameSession);
        GameSessionMongoEntity savedEntity = mongoGameplayRepository.save(entity);
        logger.info("Saved GameSession (header) with id: {}", savedEntity.getSessionId());
    }

    @Override
    public Optional<GameSession> loadGameSessionById(SessionId sessionId) {
        Optional<GameSessionMongoEntity> entity = mongoGameplayRepository.findBySessionId(sessionId.sessionsId());
        return entity.map(GameSessionMongoMapper::toDomain);
    }


    @Override
    public void addGameState(SessionId sessionId, GameState gameState, AiMove aiMove) {
        // no DTO here, pure domain
        UUID idValue = sessionId.sessionsId();
        Query query = new Query(Criteria.where("_id").is(idValue));

        // if you have an annotation DTO, map it here
        GameStateMongoEmbedded embedded = GameSessionMongoMapper.toEmbeddedState(gameState, aiMove);

        Update update = new Update().push("game_states", embedded);
        mongoTemplate.updateFirst(query, update, GameSessionMongoEntity.class);
    }

}

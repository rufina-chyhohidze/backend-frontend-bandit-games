package kdg.be.banditgames.gameplay.adapter.out;

import kdg.be.banditgames.gameplay.domain.GameSession;
import kdg.be.banditgames.gameplay.domain.GameState;
import kdg.be.banditgames.gameplay.domain.vo.SessionId;
import kdg.be.banditgames.gameplay.port.out.LoadGameSessionPort;
import kdg.be.banditgames.gameplay.port.out.PersistGameSessionPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.core.query.Query;
import java.util.Optional;
import java.util.UUID;

@Repository
public class GameSessionMongoAdapter implements PersistGameSessionPort, LoadGameSessionPort { //implements ports
    private final MongoGameplayRepository mongoGameplayRepository;
    private final MongoTemplate mongoTemplate;
    private final Logger logger = LoggerFactory.getLogger(GameSessionMongoAdapter.class);
    
    public GameSessionMongoAdapter(MongoGameplayRepository mongoGameplayRepository, 
                                   MongoTemplate mongoTemplate
    ) {
        this.mongoTemplate = mongoTemplate;
        this.mongoGameplayRepository = mongoGameplayRepository;
    }
    
    @Override
    public GameSession save(GameSession gameSession) {
        GameSessionMongoEntity entity = GameSessionMongoMapper.fromDomain(gameSession);
        GameSessionMongoEntity savedEntity = mongoGameplayRepository.save(entity);
        logger.info("Saved GameSession with id: {}", savedEntity.getSessionId());
        return GameSessionMongoMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<GameSession> loadGameSessionById(SessionId sessionId) {
        Optional<GameSessionMongoEntity> entity = mongoGameplayRepository.findBySessionId(sessionId.sessionsId());
        return entity.map(GameSessionMongoMapper::toDomain);
    }

    @Override
    public void addGameState(SessionId sessionId, GameState gameState) {
        UUID idValue = sessionId.sessionsId();

        Query query = new Query(Criteria.where("_id").is(idValue));

        Update update = new Update().push("game_states", gameState);

        mongoTemplate.updateFirst(
                query,
                update,
                GameSessionMongoEntity.class
        );
    }
}

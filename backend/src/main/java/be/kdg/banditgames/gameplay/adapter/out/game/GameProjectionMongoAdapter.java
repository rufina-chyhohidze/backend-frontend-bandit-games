package be.kdg.banditgames.gameplay.adapter.out.game;

import be.kdg.banditgames.gameplay.domain.GameProjection;
import be.kdg.banditgames.gameplay.port.out.game.LoadGameProjectionPort;
import be.kdg.banditgames.gameplay.port.out.game.SaveGameProjectionPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class GameProjectionMongoAdapter implements LoadGameProjectionPort, SaveGameProjectionPort {
    private final GameProjectionRepository repository;
    private final Logger logger = LoggerFactory.getLogger(GameProjectionMongoAdapter.class);

    public GameProjectionMongoAdapter(GameProjectionRepository repository) {
        this.repository = repository;
    }


    @Override
    public Optional<GameProjection> findByName(String name) {
        logger.info("Find game: " + name + " in MongoDB");
        Optional<GameProjectionMongoEntity> entity = repository.findGameProjectionMongoEntityByName(name);
        return entity.map(GameProjectionMongoMapper::toDomain);
    }

    @Override
    public void saveGameProjection(UUID gameId, String name) {
        logger.info("Saving game: " + name + " in MongoDB");
        GameProjectionMongoEntity entity = new GameProjectionMongoEntity(gameId, name);
        repository.save(entity);
    }
}

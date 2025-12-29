package be.kdg.banditgames.gameplay.adapter.out.game;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

public interface GameProjectionRepository extends MongoRepository<GameProjectionMongoEntity, UUID> {
    Optional<GameProjectionMongoEntity> findGameProjectionMongoEntityByName(String name);
}

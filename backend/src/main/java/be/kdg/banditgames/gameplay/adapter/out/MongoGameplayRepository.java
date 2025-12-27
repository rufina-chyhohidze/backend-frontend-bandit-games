package be.kdg.banditgames.gameplay.adapter.out;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MongoGameplayRepository extends MongoRepository<GameSessionMongoEntity, UUID> {
    Optional<GameSessionMongoEntity> findBySessionId(UUID sessionId);

    List<GameSessionMongoEntity> findAllByOrderByStartTimeDesc();
}

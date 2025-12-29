package be.kdg.banditgames.gameplay.adapter.out.aiMetadataPending;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

public interface MongoAiMetadataPendingRepository extends MongoRepository<AiMetadataPendingEntity, UUID> {
    Optional<AiMetadataPendingEntity> findBySessionIdAndMoveNumber(UUID sessionId, int moveNumber);
}

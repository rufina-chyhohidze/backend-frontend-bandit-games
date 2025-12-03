package be.kdg.banditgames.gameplay.adapter.out.aiMetadataPending;

import be.kdg.banditgames.gameplay.port.in.AiMoveMetadata;
import be.kdg.banditgames.gameplay.port.out.aiMetadataPending.DeleteAiMetadataPendingPort;
import be.kdg.banditgames.gameplay.port.out.aiMetadataPending.LoadAiMetadataPendingPort;
import be.kdg.banditgames.gameplay.port.out.aiMetadataPending.SaveAiMetadataPendingPort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public class AiMetadataPendingAdapter
        implements SaveAiMetadataPendingPort, LoadAiMetadataPendingPort, DeleteAiMetadataPendingPort {

    private final MongoAiMetadataPendingRepository repository;
    private final MongoTemplate mongoTemplate;

    public AiMetadataPendingAdapter(
            MongoAiMetadataPendingRepository repository,
            MongoTemplate mongoTemplate
    ) {
        this.repository = repository;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void saveTemporaryMetadata(UUID sessionId, int moveNumber, AiMoveMetadata metadata) {
        AiMetadataPendingEntity entity = new AiMetadataPendingEntity(
                sessionId,
                moveNumber,
                AiMetadataPendingMapper.toEmbedded(metadata),
                LocalDateTime.now()
        );

        repository.save(entity);
    }

    @Override
    public Optional<AiMetadataPendingEntity> find(UUID sessionId, int moveNumber) {
        return repository.findBySessionIdAndMoveNumber(sessionId, moveNumber);
    }

    @Override
    public void delete(AiMetadataPendingEntity entity) {
        repository.delete(entity);
    }

    /**
     * Atomically finds AND deletes a pending metadata record in a single database operation.
     * Guarantees no concurrency collisions even with double-AI turns.
     */
    @Override
    public Optional<AiMetadataPendingEntity> findAndDelete(UUID sessionId, int moveNumber) {
        Query query = new Query()
                .addCriteria(Criteria.where("sessionId").is(sessionId))
                .addCriteria(Criteria.where("moveNumber").is(moveNumber));

        AiMetadataPendingEntity removed =
                mongoTemplate.findAndRemove(query, AiMetadataPendingEntity.class);

        return Optional.ofNullable(removed);
    }
}

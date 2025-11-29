package be.kdg.banditgames.gameplay.adapter.out.aiMetadataPending;

import be.kdg.banditgames.gameplay.port.in.AiMoveMetadata;
import be.kdg.banditgames.gameplay.port.out.aiMetadataPending.DeleteAiMetadataPendingPort;
import be.kdg.banditgames.gameplay.port.out.aiMetadataPending.LoadAiMetadataPendingPort;
import be.kdg.banditgames.gameplay.port.out.aiMetadataPending.SaveAiMetadataPendingPort;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public class AiMetadataPendingAdapter implements SaveAiMetadataPendingPort, LoadAiMetadataPendingPort, DeleteAiMetadataPendingPort {
    private final MongoAiMetadataPendingRepository repository;

    public AiMetadataPendingAdapter(MongoAiMetadataPendingRepository repository) {
        this.repository = repository;
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
        return repository
                .findBySessionIdAndMoveNumber(sessionId, moveNumber);
    }

    @Override
    public void delete(AiMetadataPendingEntity entity) {
        repository.delete(entity);
    }
}

package be.kdg.banditgames.gameplay.port.out.aiMetadataPending;

import be.kdg.banditgames.gameplay.adapter.out.AiMetadataEmbedded;
import be.kdg.banditgames.gameplay.adapter.out.aiMetadataPending.AiMetadataPendingEntity;

import java.util.Optional;
import java.util.UUID;

public interface LoadAiMetadataPendingPort {
    Optional<AiMetadataPendingEntity> find(UUID sessionId, int moveNumber);
    Optional<AiMetadataPendingEntity> findAndDelete(UUID sessionId, int moveNumber);

}

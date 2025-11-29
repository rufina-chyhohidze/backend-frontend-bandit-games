package be.kdg.banditgames.gameplay.port.out.aiMetadataPending;

import be.kdg.banditgames.gameplay.adapter.out.aiMetadataPending.AiMetadataPendingEntity;

public interface DeleteAiMetadataPendingPort {
    void delete(AiMetadataPendingEntity entity);
}

package be.kdg.banditgames.gameplay.port.out.aiMetadataPending;

import be.kdg.banditgames.gameplay.port.in.AiMoveMetadata;

import java.util.UUID;

public interface SaveAiMetadataPendingPort {
    void saveTemporaryMetadata(UUID sessionId, int moveNumber, AiMoveMetadata metadata);
}

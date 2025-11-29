package be.kdg.banditgames.gameplay.adapter.out.aiMetadataPending;

import be.kdg.banditgames.gameplay.adapter.out.AiMetadataEmbedded;
import be.kdg.banditgames.gameplay.port.in.AiMoveMetadata;
import org.springframework.stereotype.Component;

@Component
public class AiMetadataPendingMapper {
    public static AiMetadataEmbedded toEmbedded(AiMoveMetadata metadata) {
        return new AiMetadataEmbedded(
                metadata.recommendedMove(),
                metadata.confidenceScore(),
                metadata.bestMove(),
                metadata.heuristicScore(),
                metadata.visitCount(),
                metadata.searchDepth()
        );
    }
}

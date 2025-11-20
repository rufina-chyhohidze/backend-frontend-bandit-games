package be.kdg.banditgames.gameplay.port.out.recommendedMove;

import be.kdg.banditgames.gameplay.adapter.out.recommendedMove.AiRequest;
import be.kdg.banditgames.gameplay.domain.RecommendedMove;

public interface RecommendedMovePort {
    
    RecommendedMove getRecommendedMove(AiRequest aiRequest);
    
}

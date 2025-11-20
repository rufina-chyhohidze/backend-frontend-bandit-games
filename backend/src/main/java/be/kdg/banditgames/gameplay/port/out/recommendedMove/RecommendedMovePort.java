package be.kdg.banditgames.gameplay.port.out.recommendedMove;

import be.kdg.banditgames.gameplay.domain.GameState;
import be.kdg.banditgames.gameplay.domain.RecommendedMove;

public interface RecommendedMovePort {
    
    RecommendedMove getRecommendedMove(GameState gameState);
    
}

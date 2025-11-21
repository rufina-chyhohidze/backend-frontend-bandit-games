package be.kdg.banditgames.gameplay.port.out.aiAgentMove;

import be.kdg.banditgames.gameplay.adapter.out.recommendedMove.AiRequest;
import be.kdg.banditgames.gameplay.domain.RecommendedMove;

public interface AiAgentMove {
    
    RecommendedMove getAiAgentMove(AiRequest aiRequest);
    
}

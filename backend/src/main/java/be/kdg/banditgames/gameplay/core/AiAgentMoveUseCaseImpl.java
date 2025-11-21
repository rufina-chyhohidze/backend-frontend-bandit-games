package be.kdg.banditgames.gameplay.core;

import be.kdg.banditgames.gameplay.adapter.out.recommendedMove.AiRequest;
import be.kdg.banditgames.gameplay.domain.RecommendedMove;
import be.kdg.banditgames.gameplay.port.out.aiAgentMove.AiAgentMove;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AiAgentMoveUseCaseImpl {
    private final AiAgentMove aiAgentMove;


    public AiAgentMoveUseCaseImpl(AiAgentMove aiAgentMove) {
        this.aiAgentMove = aiAgentMove;
    }
    
    public RecommendedMove handleNewMove(AiRequest aiRequest) {
        return aiAgentMove.getAiAgentMove(aiRequest);
    }
}

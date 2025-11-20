package be.kdg.banditgames.gameplay.core;

import be.kdg.banditgames.gameplay.adapter.out.recommendedMove.AiRequest;
import be.kdg.banditgames.gameplay.domain.RecommendedMove;
import be.kdg.banditgames.gameplay.port.out.recommendedMove.RecommendedMovePort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RecommendedMoveUseCaseImpl {
    private final RecommendedMovePort recommendedMovePort;


    public RecommendedMoveUseCaseImpl(RecommendedMovePort recommendedMovePort) {
        this.recommendedMovePort = recommendedMovePort;
    }
    
    public RecommendedMove handleNewMove(AiRequest aiRequest) {
        return recommendedMovePort.getRecommendedMove(aiRequest);
    }
}

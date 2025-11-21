package be.kdg.banditgames.gameplay.core;

import be.kdg.banditgames.gameplay.domain.RecommendedMove;
import be.kdg.banditgames.gameplay.port.in.MLRecommendedMoveCommand.GetRecommendedMoveCommand;
import be.kdg.banditgames.gameplay.port.out.MLRecommendedMove.MLRecommendedMoveService;
import be.kdg.banditgames.gameplay.port.out.MLRecommendedMove.MLRecommendedMoveUseCase;
import org.springframework.stereotype.Service;

@Service
public class RecommendedMoveUseCaseImpl implements MLRecommendedMoveUseCase {
    
    private final MLRecommendedMoveService recommendedMoveService;
    
    public RecommendedMoveUseCaseImpl(MLRecommendedMoveService recommendedMoveService) {
        this.recommendedMoveService = recommendedMoveService;
    }

    @Override
    public RecommendedMove handleMove(GetRecommendedMoveCommand getRecommendedMoveCommand) {
        return recommendedMoveService.getRecommendedMove(getRecommendedMoveCommand);
    }
}

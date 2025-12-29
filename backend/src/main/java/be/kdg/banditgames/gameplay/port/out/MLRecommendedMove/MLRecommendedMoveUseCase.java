package be.kdg.banditgames.gameplay.port.out.MLRecommendedMove;

import be.kdg.banditgames.gameplay.domain.RecommendedMove;
import be.kdg.banditgames.gameplay.port.in.MLRecommendedMove.GetRecommendedMoveCommand;

public interface MLRecommendedMoveUseCase {
    
    RecommendedMove handleMove(GetRecommendedMoveCommand getRecommendedMoveCommand);
    
}

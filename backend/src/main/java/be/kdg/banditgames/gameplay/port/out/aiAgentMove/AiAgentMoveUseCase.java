package be.kdg.banditgames.gameplay.port.out.aiAgentMove;

import be.kdg.banditgames.gameplay.adapter.out.aiAgentMove.AiRequestDto;
import be.kdg.banditgames.gameplay.domain.AiMove;
import be.kdg.banditgames.gameplay.domain.RecommendedMove;
import be.kdg.banditgames.gameplay.port.in.AiRequestCommand;

public interface AiAgentMoveUseCase {

    AiMove handleMove(AiRequestCommand aiRequest);
    
}

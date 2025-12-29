package be.kdg.banditgames.gameplay.port.out.aiAgentMove;

import be.kdg.banditgames.gameplay.port.in.AiMoveMetadata;
import be.kdg.banditgames.gameplay.port.in.AiRequestCommand;

public interface AiAgentMoveUseCase {

    AiMoveMetadata handleMove(AiRequestCommand aiRequest);
    
}

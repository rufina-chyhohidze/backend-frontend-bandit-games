package be.kdg.banditgames.gameplay.port.out.aiAgentMove;

import be.kdg.banditgames.gameplay.domain.AiMove;
import be.kdg.banditgames.gameplay.port.in.AiRequestCommand;

public interface AiAgenteMoveService {
    AiMove getAiAgentMove(AiRequestCommand aiRequest);
}

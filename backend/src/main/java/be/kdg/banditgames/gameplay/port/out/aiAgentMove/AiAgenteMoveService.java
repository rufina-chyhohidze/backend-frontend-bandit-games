package be.kdg.banditgames.gameplay.port.out.aiAgentMove;

import be.kdg.banditgames.gameplay.domain.AiMove;
import be.kdg.banditgames.gameplay.port.in.AiMoveMetadata;
import be.kdg.banditgames.gameplay.port.in.AiRequestCommand;

public interface AiAgenteMoveService {
    AiMoveMetadata getAiAgentMove(AiRequestCommand aiRequest);
}

package be.kdg.banditgames.gameplay.core;

import be.kdg.banditgames.gameplay.port.in.AiMoveMetadata;
import be.kdg.banditgames.gameplay.port.in.AiRequestCommand;
import be.kdg.banditgames.gameplay.port.out.aiAgentMove.AiAgentMoveUseCase;
import be.kdg.banditgames.gameplay.port.out.aiAgentMove.AiAgentMoveService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AiAgentMoveUseCaseImpl implements AiAgentMoveUseCase{
    private final AiAgentMoveService aiAgentMoveService;


    public AiAgentMoveUseCaseImpl(AiAgentMoveService aiAgentMoveService) {
        this.aiAgentMoveService = aiAgentMoveService;
    }
    
    @Override
    public AiMoveMetadata handleMove(AiRequestCommand aiRequest) {
        AiMoveMetadata aiMove = aiAgentMoveService.getAiAgentMove(aiRequest);
        return aiMove;
    }
}

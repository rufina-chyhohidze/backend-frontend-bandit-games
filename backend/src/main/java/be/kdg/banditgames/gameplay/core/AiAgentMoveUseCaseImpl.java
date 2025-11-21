package be.kdg.banditgames.gameplay.core;

import be.kdg.banditgames.gameplay.domain.AiMove;
import be.kdg.banditgames.gameplay.port.in.AiRequestCommand;
import be.kdg.banditgames.gameplay.port.out.aiAgentMove.AiAgentMoveUseCase;
import be.kdg.banditgames.gameplay.port.out.aiAgentMove.AiAgenteMoveService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AiAgentMoveUseCaseImpl implements AiAgentMoveUseCase{
    private final AiAgenteMoveService aiAgentMoveService;


    public AiAgentMoveUseCaseImpl(AiAgenteMoveService aiAgentMoveService) {
        this.aiAgentMoveService = aiAgentMoveService;
    }
    
    @Override
    public AiMove handleMove(AiRequestCommand aiRequest) {
        return aiAgentMoveService.getAiAgentMove(aiRequest);
    }
}

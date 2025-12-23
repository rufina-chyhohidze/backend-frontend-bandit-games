package be.kdg.banditgames.gameplay.core;

import be.kdg.banditgames.gameplay.port.in.AiMoveMetadata;
import be.kdg.banditgames.gameplay.port.in.AiRequestCommand;
import be.kdg.banditgames.gameplay.port.in.winProbability.GetWinProbabilityCommand; //
import be.kdg.banditgames.gameplay.port.out.aiAgentMove.AiAgentMoveUseCase;
import be.kdg.banditgames.gameplay.port.out.aiAgentMove.AiAgentMoveService;
import be.kdg.banditgames.gameplay.port.out.mlWinProbability.MLWinProbabilityUseCase; //
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AiAgentMoveUseCaseImpl implements AiAgentMoveUseCase {
    private final AiAgentMoveService aiAgentMoveService;
    private final MLWinProbabilityUseCase winProbabilityUseCase;

    public AiAgentMoveUseCaseImpl(AiAgentMoveService aiAgentMoveService,
                                  MLWinProbabilityUseCase winProbabilityUseCase) {
        this.aiAgentMoveService = aiAgentMoveService;
        this.winProbabilityUseCase = winProbabilityUseCase;
    }

    @Override
    public AiMoveMetadata handleMove(AiRequestCommand aiRequest) {
        AiMoveMetadata aiMove = aiAgentMoveService.getAiAgentMove(aiRequest);

            winProbabilityUseCase.handleWinProbability(new GetWinProbabilityCommand(
                    aiRequest.sessionId(),
                    aiRequest.moveNumber(),
                    aiRequest.playerType().name(),
                    aiRequest.gameState(),
                    aiRequest.legalMoves()
            ));


        return aiMove;
    }
}
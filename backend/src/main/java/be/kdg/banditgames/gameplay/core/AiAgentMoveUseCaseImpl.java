package be.kdg.banditgames.gameplay.core;

import be.kdg.banditgames.gameplay.domain.events.AiMoveCalculatedInternalEvent;
import be.kdg.banditgames.gameplay.port.in.AiMoveMetadata;
import be.kdg.banditgames.gameplay.port.in.AiRequestCommand;
import be.kdg.banditgames.gameplay.port.out.aiAgentMove.AiAgentMoveUseCase;
import be.kdg.banditgames.gameplay.port.out.aiAgentMove.AiAgentMoveService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AiAgentMoveUseCaseImpl implements AiAgentMoveUseCase {
    private static final Logger logger = LoggerFactory.getLogger(AiAgentMoveUseCaseImpl.class);

    private final AiAgentMoveService aiAgentMoveService;
    private final ApplicationEventPublisher eventPublisher;

    public AiAgentMoveUseCaseImpl(AiAgentMoveService aiAgentMoveService,
                                   ApplicationEventPublisher eventPublisher) {
        this.aiAgentMoveService = aiAgentMoveService;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public AiMoveMetadata handleMove(AiRequestCommand aiRequest) {
        // Get MCTS move
        AiMoveMetadata aiMove = aiAgentMoveService.getAiAgentMove(aiRequest);

        // Publish internal event to trigger async ML recommendation for game analysis
        // Use moveNumber + 1 to match actual game state numbering (AI moves after human)
        int actualGameMoveNumber = aiRequest.moveNumber() + 1;
        logger.info("Publishing AiMoveCalculatedInternalEvent for async ML recommendation, actualMoveNumber={}", actualGameMoveNumber);
        eventPublisher.publishEvent(new AiMoveCalculatedInternalEvent(
                aiRequest.sessionId(),
                actualGameMoveNumber,
                aiRequest.playerType().name(),
                aiRequest.gameState(),
                aiRequest.legalMoves()
        ));

        // Return immediately - ML recommendation will be processed asynchronously
        return aiMove;
    }
}

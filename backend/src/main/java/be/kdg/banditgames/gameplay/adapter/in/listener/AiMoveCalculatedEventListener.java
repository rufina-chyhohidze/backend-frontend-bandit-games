package be.kdg.banditgames.gameplay.adapter.in.listener;

import be.kdg.banditgames.common.events.ml.MLRecommendedMoveEvent;
import be.kdg.banditgames.common.events.ml.MLWinProbabilityEvent;
import be.kdg.banditgames.gameplay.adapter.out.mlRecommendedMove.MLRecommendedMoveEventPublisher;
import be.kdg.banditgames.gameplay.adapter.out.mlWinProbability.MLWinProbabilityPublisher;
import be.kdg.banditgames.gameplay.domain.RecommendedMove;
import be.kdg.banditgames.gameplay.domain.WinProbability;
import be.kdg.banditgames.gameplay.domain.events.AiMoveCalculatedInternalEvent;
import be.kdg.banditgames.gameplay.port.in.MLRecommendedMove.GetRecommendedMoveCommand;
import be.kdg.banditgames.gameplay.port.in.winProbability.GetWinProbabilityCommand;
import be.kdg.banditgames.gameplay.port.out.MLRecommendedMove.MLRecommendedMoveService;
import be.kdg.banditgames.gameplay.port.out.mlWinProbability.MLWinProbabilityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class AiMoveCalculatedEventListener {

    private static final Logger logger = LoggerFactory.getLogger(AiMoveCalculatedEventListener.class);

    private final MLRecommendedMoveService mlRecommendedMoveService;
    private final MLRecommendedMoveEventPublisher mlEventPublisher;
    private final MLWinProbabilityService mlWinProbabilityService;
    private final MLWinProbabilityPublisher mlWinProbabilityPublisher;

    public AiMoveCalculatedEventListener(MLRecommendedMoveService mlRecommendedMoveService,
                                         MLRecommendedMoveEventPublisher mlEventPublisher,
                                         MLWinProbabilityService mlWinProbabilityService,
                                         MLWinProbabilityPublisher mlWinProbabilityPublisher) {
        this.mlRecommendedMoveService = mlRecommendedMoveService;
        this.mlEventPublisher = mlEventPublisher;
        this.mlWinProbabilityService = mlWinProbabilityService;
        this.mlWinProbabilityPublisher = mlWinProbabilityPublisher;
    }

    @EventListener
    public void onAiMoveCalculated(AiMoveCalculatedInternalEvent event) {
        logger.info("Received AiMoveCalculatedInternalEvent for session: {}, move: {}",
                event.sessionId(), event.moveNumber());

        try {
            RecommendedMove mlMove = mlRecommendedMoveService.getRecommendedMove(
                    new GetRecommendedMoveCommand(
                            event.sessionId(),
                            event.moveNumber(),
                            event.aiType(),
                            event.gameState(),
                            event.legalMoves()
                    )
            );

            mlEventPublisher.publish(new MLRecommendedMoveEvent(
                    event.sessionId(),
                    event.moveNumber(),
                    event.aiType(),
                    event.gameState(),
                    event.legalMoves(),
                    mlMove.move(),
                    mlMove.confidenceScore()
            ));

            WinProbability winProb = mlWinProbabilityService.getWinProbability(
                    new GetWinProbabilityCommand(
                            event.sessionId(),
                            event.moveNumber(),
                            event.aiType(),
                            event.gameState(),
                            event.legalMoves()
                    )
            );

            mlWinProbabilityPublisher.publish(new MLWinProbabilityEvent(
                    event.sessionId(),
                    event.moveNumber(),
                    event.aiType(),
                    event.gameState(),
                    event.legalMoves(),
                    winProb.player1WinProbability(),
                    winProb.player2WinProbability(),
                    winProb.activePlayerWinProbability(),
                    winProb.distribution()
            ));

            logger.info("Published ML Analysis Events (Move & WinProb) for session: {}", event.sessionId());

        } catch (Exception e) {
            logger.warn("Failed to get ML analysis for MCTS move: {}", e.getMessage());
        }
    }
}
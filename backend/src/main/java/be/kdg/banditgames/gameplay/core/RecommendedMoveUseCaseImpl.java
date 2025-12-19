package be.kdg.banditgames.gameplay.core;

import be.kdg.banditgames.gameplay.adapter.out.mlRecommendedMove.MLRecommendedMoveEvent;
import be.kdg.banditgames.gameplay.adapter.out.mlRecommendedMove.MLRecommendedMoveEventPublisher;
import be.kdg.banditgames.gameplay.domain.RecommendedMove;
import be.kdg.banditgames.gameplay.port.in.MLRecommendedMove.GetRecommendedMoveCommand;
import be.kdg.banditgames.gameplay.port.out.MLRecommendedMove.MLRecommendedMoveService;
import be.kdg.banditgames.gameplay.port.out.MLRecommendedMove.MLRecommendedMoveUseCase;
import org.springframework.stereotype.Service;

@Service
public class RecommendedMoveUseCaseImpl implements MLRecommendedMoveUseCase {

    private final MLRecommendedMoveService recommendedMoveService;
    private final MLRecommendedMoveEventPublisher eventPublisher;

    public RecommendedMoveUseCaseImpl(
            MLRecommendedMoveService recommendedMoveService,
            MLRecommendedMoveEventPublisher eventPublisher
    ) {
        this.recommendedMoveService = recommendedMoveService;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public RecommendedMove handleMove(GetRecommendedMoveCommand command) {
        RecommendedMove recommendedMove =
                recommendedMoveService.getRecommendedMove(command);

        eventPublisher.publish(new MLRecommendedMoveEvent(
                command.sessionId(),
                command.moveNumber(),
                command.aiType(),
                command.gameState(),
                command.legalMoves(),
                recommendedMove.move(),
                recommendedMove.confidenceScore()
        ));

        return recommendedMove;
    }

}

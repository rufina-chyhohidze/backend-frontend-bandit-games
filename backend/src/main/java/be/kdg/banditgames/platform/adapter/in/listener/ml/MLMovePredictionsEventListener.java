package be.kdg.banditgames.platform.adapter.in.listener.ml;

import be.kdg.banditgames.common.events.ml.MLRecommendedMoveEvent;
import be.kdg.banditgames.platform.port.in.ml.AddMoveRecommendedPort;
import be.kdg.banditgames.platform.port.in.ml.CreateMoveRecommendedCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

@Component
public class MLMovePredictionsEventListener {
    private final Logger logger = LoggerFactory.getLogger(MLMovePredictionsEventListener.class);
    private final AddMoveRecommendedPort winProbabilityPort;

    public MLMovePredictionsEventListener(AddMoveRecommendedPort winProbabilityPort) {
        this.winProbabilityPort = winProbabilityPort;
    }
    @ApplicationModuleListener
    public void createWinProbability(MLRecommendedMoveEvent event){
        logger.info("create win probability in platform");
        winProbabilityPort.addMoveProbability(new CreateMoveRecommendedCommand(
                event.sessionId(),
                event.moveNumber(),
                event.aiType(),
                event.gameState(),
                event.legalMoves(),
                event.move(),
                event.confidenceScore()));
    }
}
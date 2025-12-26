package be.kdg.banditgames.platform.adapter.in.listener.ml;

import be.kdg.banditgames.common.events.ml.MLWinProbabilityEvent;
import be.kdg.banditgames.platform.port.in.ml.AddWinProbabilityPort;
import be.kdg.banditgames.platform.port.in.ml.CreateWinProbabilityCommand;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;

@Component
public class MLWinProbabilityEventListener {
    private final Logger logger = LoggerFactory.getLogger(MLWinProbabilityEventListener.class);
    private final AddWinProbabilityPort winProbabilityPort;

    public MLWinProbabilityEventListener(AddWinProbabilityPort winProbabilityPort) {
        this.winProbabilityPort = winProbabilityPort;
    }

    @EventListener
    public void createWinProbability(MLWinProbabilityEvent event){
        logger.info("Received MLWinProbabilityEvent for session: {}, move: {}",
                    event.sessionId(), event.moveNumber());
        try {
            winProbabilityPort.addWinProbability(new CreateWinProbabilityCommand(
                    event.sessionId(),
                    event.moveNumber(),
                    event.aiType(),
                    event.gameState(),
                    event.legalMoves(),
                    event.player1WinProbability(),
                    event.player2WinProbability(),
                    event.activePlayerWinProbability(),
                    event.distribution()
            ));
            logger.info("Successfully stored win probability for session: {}", event.sessionId());
        } catch (Exception e) {
            logger.error("Failed to store win probability: {}", e.getMessage(), e);
        }
    }
}
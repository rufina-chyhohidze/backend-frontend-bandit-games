package be.kdg.banditgames.platform.adapter.in.listener.ml;

import be.kdg.banditgames.common.events.ml.MLWinProbabilityEvent;
import be.kdg.banditgames.platform.port.in.ml.AddWinProbabilityPort;
import be.kdg.banditgames.platform.port.in.ml.CreateWinProbabilityCommand;
import org.slf4j.LoggerFactory;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;

import java.util.List;
import java.util.UUID;

@Component
public class MLWinProbabilityEventListener {
    private final Logger logger = LoggerFactory.getLogger(MLWinProbabilityEventListener.class);
    private final AddWinProbabilityPort winProbabilityPort;

    public MLWinProbabilityEventListener(AddWinProbabilityPort winProbabilityPort) {
        this.winProbabilityPort = winProbabilityPort;
    }

    @ApplicationModuleListener
    public void createWinProbability(MLWinProbabilityEvent event){
        logger.info("create win probability in platform");
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
    }
}
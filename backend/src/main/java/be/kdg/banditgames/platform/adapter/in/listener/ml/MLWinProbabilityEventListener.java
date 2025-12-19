package be.kdg.banditgames.platform.adapter.in.listener.ml;

import be.kdg.banditgames.platform.port.in.ml.AddWinProbabilityPort;
import be.kdg.banditgames.platform.port.in.ml.CreateWinProbabilityCommand;
import org.slf4j.LoggerFactory;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
@Component
public class MLWinProbabilityEventListener {
    private final Logger logger = LoggerFactory.getLogger(MLWinProbabilityEventListener.class);
    private final AddWinProbabilityPort winProbabilityPort;

    public MLWinProbabilityEventListener(AddWinProbabilityPort winProbabilityPort) {
        this.winProbabilityPort = winProbabilityPort;
    }

    //TODO add event and full fill the command
    @ApplicationModuleListener
    public void createWinProbability(){
        logger.info("create win probability in platform");
        winProbabilityPort.addWinProbability(new CreateWinProbabilityCommand());
    }
}

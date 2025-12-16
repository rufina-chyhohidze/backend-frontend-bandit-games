package be.kdg.banditgames.gameplay.adapter.in.listener;

import be.kdg.banditgames.common.events.generic.GenericAcceptedGameEvent;
import be.kdg.banditgames.gameplay.port.in.game.CreateGamePort;
import org.slf4j.LoggerFactory;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class GameAcceptedEventListener {

    private final Logger logger = LoggerFactory.getLogger(GameAcceptedEventListener.class);
    private final CreateGamePort gamePort;

    public GameAcceptedEventListener(CreateGamePort gamePort) {
        this.gamePort = gamePort;
    }

    @ApplicationModuleListener
    public void gameAcceptedEvent(GenericAcceptedGameEvent acceptedGameEvent){
        logger.info("Game has been accepted and ready to create a projection");
        gamePort.createGame(acceptedGameEvent.name(), acceptedGameEvent.gameId());
    }
}


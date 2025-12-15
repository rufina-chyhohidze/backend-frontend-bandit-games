package be.kdg.banditgames.gameplay.adapter.in.listener.generalGames;

import be.kdg.banditgames.common.events.connect4.Connect4GameCreatedEvent;
import be.kdg.banditgames.common.events.generic.GenericGameCreatedEvent;
import be.kdg.banditgames.gameplay.port.in.GameCreatedCommand;
import be.kdg.banditgames.gameplay.port.in.GameCreatedPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class GeneralGameCreatedEventListener {

    private final Logger log = LoggerFactory.getLogger(GeneralGameCreatedEventListener.class);
    private final GameCreatedPort gameCreatedPort;
    
    public GeneralGameCreatedEventListener(GameCreatedPort gameCreatedPort) {
        this.gameCreatedPort = gameCreatedPort;
    }

    @ApplicationModuleListener
    public void gameCreatedEvent(GenericGameCreatedEvent gameCreatedEvent) {
        log.info("Game Game created: {}", gameCreatedEvent);

        gameCreatedPort.project(new GameCreatedCommand(
                gameCreatedEvent.eventId(),
                gameCreatedEvent.occurredAt(),
                UUID.fromString(gameCreatedEvent.sessionId()),
                gameCreatedEvent.player1(),
                gameCreatedEvent.player2()
        ));
    }
    

}

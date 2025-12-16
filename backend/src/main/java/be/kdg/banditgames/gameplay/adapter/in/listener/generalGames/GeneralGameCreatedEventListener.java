package be.kdg.banditgames.gameplay.adapter.in.listener.generalGames;

import be.kdg.banditgames.common.events.generic.GenericGameCreatedEvent;
import be.kdg.banditgames.gameplay.port.in.gameSession.GameSessionCreatedCommand;
import be.kdg.banditgames.gameplay.port.in.gameSession.GameSessionCreatedPort;
import be.kdg.banditgames.gameplay.port.in.game.LoadGameByNamePort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class GeneralGameCreatedEventListener {

    private final Logger log = LoggerFactory.getLogger(GeneralGameCreatedEventListener.class);
    private final GameSessionCreatedPort gameSessionCreatedPort;
    private final LoadGameByNamePort findGamePort;

    public GeneralGameCreatedEventListener(GameSessionCreatedPort gameSessionCreatedPort, LoadGameByNamePort findGamePort) {
        this.gameSessionCreatedPort = gameSessionCreatedPort;
        this.findGamePort = findGamePort;
    }

    @ApplicationModuleListener
    public void gameCreatedEvent(GenericGameCreatedEvent gameCreatedEvent) {
        log.info("Game Game created: {}", gameCreatedEvent);

        gameSessionCreatedPort.project(new GameSessionCreatedCommand(
                gameCreatedEvent.eventId(),
                gameCreatedEvent.occurredAt(),
                UUID.fromString(gameCreatedEvent.sessionId()),
                findGamePort.findByName(gameCreatedEvent.name()).gameId(),
                gameCreatedEvent.player1(),
                gameCreatedEvent.player2()
        ));
    }
    

}

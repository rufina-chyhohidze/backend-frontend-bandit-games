package be.kdg.banditgames.gameplay.adapter.in.listener.connect4;

import be.kdg.banditgames.common.config.RabbitMQTopology;
import be.kdg.banditgames.common.events.connect4.Connect4GameCreatedEvent;
import be.kdg.banditgames.gameplay.port.in.game.LoadGameByNamePort;
import be.kdg.banditgames.gameplay.port.in.gameSession.GameSessionCreatedCommand;
import be.kdg.banditgames.gameplay.port.in.gameSession.GameSessionCreatedPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class GameCreatedEventListener {

    private final Logger log = LoggerFactory.getLogger(GameCreatedEventListener.class);
    private final GameSessionCreatedPort gameSessionCreatedPort;
    private final LoadGameByNamePort findGamePort;

    public GameCreatedEventListener(GameSessionCreatedPort gameSessionCreatedPort, LoadGameByNamePort findGamePort) {
        this.gameSessionCreatedPort = gameSessionCreatedPort;
        this.findGamePort = findGamePort;
    }

    @RabbitListener(queues = RabbitMQTopology.CONNECT4_GAME_CREATED_QUEUE)
    public void gameCreatedEvent(Connect4GameCreatedEvent gameCreatedEvent) {
        log.info("Connect4 Game created: {}", gameCreatedEvent);

        gameSessionCreatedPort.project(new GameSessionCreatedCommand(
                gameCreatedEvent.eventId(),
                gameCreatedEvent.occurredAt(),
                UUID.fromString(gameCreatedEvent.sessionId()),
                findGamePort.findByName(gameCreatedEvent.game()).gameId(),
                gameCreatedEvent.player1(),
                gameCreatedEvent.player2()
        ));
    }
    

}

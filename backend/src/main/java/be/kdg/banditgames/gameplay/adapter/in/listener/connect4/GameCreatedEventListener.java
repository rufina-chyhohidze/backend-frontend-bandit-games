package be.kdg.banditgames.gameplay.adapter.in.listener.connect4;

import be.kdg.banditgames.common.config.RabbitMQTopology;
import be.kdg.banditgames.common.events.connect4.GameCreatedEvent;
import be.kdg.banditgames.gameplay.port.in.GameCreatedCommand;
import be.kdg.banditgames.gameplay.port.in.GameCreatedPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class GameCreatedEventListener {

    private final Logger log = LoggerFactory.getLogger(GameCreatedEventListener.class);
    private final GameCreatedPort gameCreatedPort;
    
    public GameCreatedEventListener(GameCreatedPort gameCreatedPort) {
        this.gameCreatedPort = gameCreatedPort;
    }

    @RabbitListener(queues = RabbitMQTopology.CONNECT4_GAME_CREATED_QUEUE)
    public void gameCreatedEvent(GameCreatedEvent gameCreatedEvent) {
        log.info("Connect4 Game created: {}", gameCreatedEvent);

        gameCreatedPort.project(new GameCreatedCommand(
                gameCreatedEvent.eventId(),
                gameCreatedEvent.occurredAt(),
                UUID.fromString(gameCreatedEvent.sessionId()),
                UUID.fromString(gameCreatedEvent.gameId()),
                gameCreatedEvent.player1(),
                gameCreatedEvent.player2()
        ));
    }
    

}

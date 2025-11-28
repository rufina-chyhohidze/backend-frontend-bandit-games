package be.kdg.banditgames.gameplay.adapter.in.listener.connect4;

import be.kdg.banditgames.common.config.RabbitMQTopology;
import be.kdg.banditgames.common.events.MoveMadeEvent;
import be.kdg.banditgames.gameplay.port.in.MoveMadeCommand;
import be.kdg.banditgames.gameplay.port.in.MoveMadePort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MoveMadeEventListener {

    Logger log = LoggerFactory.getLogger(MoveMadeEventListener.class);
    private final MoveMadePort moveMadePort;
    
    public MoveMadeEventListener(MoveMadePort moveMadePort) {
        this.moveMadePort = moveMadePort;
    }
    
    @RabbitListener(queues = RabbitMQTopology.CONNECT4_MOVE_MADE_QUEUE)
    public void moveMadeEvent(MoveMadeEvent event) {
        log.info("Received MoveMadeEvent for game: {}", event.gameId());
        
        moveMadePort.project(new MoveMadeCommand(
                event.eventId(),
                event.occurredAt(),
                event.gameId(),
                event.sessionId(),
                event.playerType(),
                event.playerSide(),
                event.moveNumber(),
                event.serializedBoard(),
                event.serializedLegalMoves()
        ));
    }
    
    
    

}

package be.kdg.banditgames.gameplay.adapter.in.listener.connect4;

import be.kdg.banditgames.common.config.RabbitMQTopology;
import be.kdg.banditgames.common.events.connect4.Connect4MoveMadeEvent;
import be.kdg.banditgames.gameplay.domain.exceptions.GameSesssionNotFound;
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
    public void moveMadeEvent(Connect4MoveMadeEvent event) {
        log.info("Received MoveMadeEvent: {}", event);
        
        try {
            moveMadePort.project(new MoveMadeCommand(
                    event.eventId(),
                    event.occurredAt(),
                    event.sessionId(),
                    event.playerType(),
                    event.playerSide(),
                    event.moveNumber(),
                    event.serializedBoard(),
                    event.serializedLegalMoves(),
                    event.actualMove(),
                    event.aiHardRecommendedMove(),
                    event.aiHardConfidence(),
                    event.aiHardWinProbability(),
                    event.aiMlRecommendedMove(),
                    event.aiMlConfidence(),
                    event.aiMlWinProbability()));
        } catch (GameSesssionNotFound e) {
            log.warn("Game session not found for move event, session may not have been created yet or was already cleaned up: {}", event.sessionId());
        }
    }
    
    
    

}

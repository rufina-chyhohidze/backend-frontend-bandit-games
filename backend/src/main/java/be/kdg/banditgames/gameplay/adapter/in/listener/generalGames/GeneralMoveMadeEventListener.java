package be.kdg.banditgames.gameplay.adapter.in.listener.generalGames;

import be.kdg.banditgames.common.events.connect4.Connect4MoveMadeEvent;
import be.kdg.banditgames.common.events.generic.GenericMoveMadeEvent;
import be.kdg.banditgames.gameplay.port.in.MoveMadeCommand;
import be.kdg.banditgames.gameplay.port.in.MoveMadePort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

@Component
public class GeneralMoveMadeEventListener {

    Logger log = LoggerFactory.getLogger(GeneralMoveMadeEventListener.class);
    private final MoveMadePort moveMadePort;

    public GeneralMoveMadeEventListener(MoveMadePort moveMadePort) {
        this.moveMadePort = moveMadePort;
    }

    @EventListener
    public void moveMadeEvent(GenericMoveMadeEvent event) {
        log.info("Received MoveMadeEvent: {}", event);

        moveMadePort.project(new MoveMadeCommand(
                event.eventId(),
                event.occurredAt(),
                event.sessionId(),
                event.playerType(),
                event.playerSide(),
                event.moveNumber(),
                event.serializedBoard(),
                event.serializedLegalMoves()));
    }




}

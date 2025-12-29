package be.kdg.banditgames.gameplay.adapter.in.listener.generalGames;

import be.kdg.banditgames.common.events.generic.GenericGameResultEvent;
import be.kdg.banditgames.common.shared.SessionId;
import be.kdg.banditgames.gameplay.domain.GameResult;
import be.kdg.banditgames.gameplay.port.in.gameSession.GameResultsCommand;
import be.kdg.banditgames.gameplay.port.in.gameSession.GameResultsPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class GeneralGameResultEventListener {
    private final Logger log = LoggerFactory.getLogger(GeneralGameResultEventListener.class);
    private final GameResultsPort  gameResultsPort;

    public GeneralGameResultEventListener(GameResultsPort gameResultsPort) {
        this.gameResultsPort = gameResultsPort;
    }

    @EventListener
    public void gameResultEvent(GenericGameResultEvent gameResultEvent) {
        log.info("Game result: {}", gameResultEvent);

        GameResultsCommand command =  new GameResultsCommand(
                gameResultEvent.occurredAt(),
                new SessionId(UUID.fromString(gameResultEvent.sessionId())),
                GameResult.valueOf(gameResultEvent.gameResult()));

        gameResultsPort.finishGame(command);
    }

}

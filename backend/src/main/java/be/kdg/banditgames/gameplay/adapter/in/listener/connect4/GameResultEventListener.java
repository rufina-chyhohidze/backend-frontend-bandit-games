package be.kdg.banditgames.gameplay.adapter.in.listener.connect4;

import be.kdg.banditgames.common.config.RabbitMQTopology;
import be.kdg.banditgames.common.events.connect4.Connect4GameResultEvent;
import be.kdg.banditgames.common.shared.SessionId;
import be.kdg.banditgames.gameplay.domain.GameResult;
import be.kdg.banditgames.gameplay.port.in.gameSession.GameResultsCommand;
import be.kdg.banditgames.gameplay.port.in.gameSession.GameResultsPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class GameResultEventListener {
    private final Logger log = LoggerFactory.getLogger(GameResultEventListener.class);
    private final GameResultsPort  gameResultsPort;

    public GameResultEventListener(GameResultsPort gameResultsPort) {
        this.gameResultsPort = gameResultsPort;
    }

    @RabbitListener(queues = RabbitMQTopology.CONNECT4_GAME_RESULT_QUEUE)
    public void gameResultEvent(Connect4GameResultEvent gameResultEvent) {
        log.info("Game result: {}", gameResultEvent);

        GameResultsCommand command =  new GameResultsCommand(
                gameResultEvent.occurredAt(),
                new SessionId(UUID.fromString(gameResultEvent.sessionId())),
                GameResult.valueOf(gameResultEvent.gameResult()));

        gameResultsPort.finishGame(command);
    }

}

package be.kdg.banditgames.gameplay.adapter.in.listener.connect4;

import be.kdg.banditgames.common.config.RabbitMQTopology;
import be.kdg.banditgames.common.events.GameResultEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class GameResultEventListener {
    private final Logger log = LoggerFactory.getLogger(GameResultEventListener.class);

    // TODO: add and implement gameResultPort here

    @RabbitListener(queues = RabbitMQTopology.CONNECT4_GAME_RESULT_QUEUE)
    public void gameResultEvent(GameResultEvent gameResultEvent) {
        log.info("Game result: {}", gameResultEvent);

        // gameResultPort
    }

}

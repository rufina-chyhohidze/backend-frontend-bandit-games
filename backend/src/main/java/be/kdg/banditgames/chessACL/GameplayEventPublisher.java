package be.kdg.banditgames.chessACL;

import be.kdg.banditgames.common.events.connect4.Connect4GameCreatedEvent;
import be.kdg.banditgames.common.events.connect4.Connect4GameResultEvent;
import be.kdg.banditgames.common.events.connect4.Connect4MoveMadeEvent;
import be.kdg.banditgames.common.events.generic.GenericAchievementEvent;
import be.kdg.banditgames.common.events.generic.GenericGameCreatedEvent;
import be.kdg.banditgames.common.events.generic.GenericGameResultEvent;
import be.kdg.banditgames.common.events.generic.GenericMoveMadeEvent;
import be.kdg.banditgames.common.shared.PlayerSide;
import be.kdg.banditgames.common.shared.PlayerType;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class GameplayEventPublisher {

    private final ApplicationEventPublisher publisher;

    public GameplayEventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    public void publishGameCreated(String sessionId, String name) {
        
        publisher.publishEvent(new GenericGameCreatedEvent(
                sessionId,
                name,
                PlayerType.HUMAN,
                PlayerType.HUMAN
        ));
    }

    public void publishMoveMade(UUID sessionId,
                                PlayerType playerType,
                                PlayerSide playerSide,
                                int moveNumber,
                                String serializedBoard,
                                String serializedLegalMoves) {

        publisher.publishEvent(new GenericMoveMadeEvent(
                sessionId,
                playerType,
                playerSide,
                moveNumber,
                serializedBoard,
                serializedLegalMoves
        ));
    }


    public void publishGameResult(String sessionId, String gameResult) {
        publisher.publishEvent(new GenericGameResultEvent(
                sessionId,
                gameResult
        ));
    }


}

package be.kdg.banditgames.platform.adapter.out.game;

import be.kdg.banditgames.platform.domain.Game;
import be.kdg.banditgames.platform.port.out.game.UpdateGamesPort;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class GameEventPublisher implements UpdateGamesPort {
    private final ApplicationEventPublisher applicationEventPublisher;

    public GameEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public Game updateGames(Game game) {
        game.getDomainEvents().forEach(applicationEventPublisher::publishEvent);
        return game;
    }
}

package be.kdg.banditgames.platform.port.out;

import be.kdg.banditgames.platform.domain.Game;

import java.util.List;

public interface LoadPlayableGamesPort {
    List<Game> loadPlayableGames();
}

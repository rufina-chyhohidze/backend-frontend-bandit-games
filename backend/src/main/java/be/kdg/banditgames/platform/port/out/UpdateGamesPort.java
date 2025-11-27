package be.kdg.banditgames.platform.port.out;

import be.kdg.banditgames.platform.domain.Game;

public interface UpdateGamesPort {
    Game updateGames(Game game);
}

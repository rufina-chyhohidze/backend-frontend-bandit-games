package be.kdg.banditgames.platform.port.in.game;

import be.kdg.banditgames.platform.domain.Game;

import java.util.List;

public interface ListPendingGamesUseCase {
    List<Game> listPendingGames();
}

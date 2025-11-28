package be.kdg.banditgames.platform.port.out;

import be.kdg.banditgames.platform.domain.Game;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LoadPlayableGamesPort {
    List<Game> loadPlayableGames();
    Optional<Game> loadGameById(UUID gameId);
}

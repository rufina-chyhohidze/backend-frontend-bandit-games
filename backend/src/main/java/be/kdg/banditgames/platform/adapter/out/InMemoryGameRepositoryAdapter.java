package be.kdg.banditgames.platform.adapter.out;

import be.kdg.banditgames.platform.domain.Game;
import be.kdg.banditgames.platform.domain.GameStatus;
import be.kdg.banditgames.platform.port.out.LoadPlayableGamesPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class InMemoryGameRepositoryAdapter implements LoadPlayableGamesPort {
    private final List<Game> games;

    public InMemoryGameRepositoryAdapter() {
        this.games = List.of(
                new Game(
                        UUID.fromString("00000000-0000-0000-0000-000000000001"),
                        "Connect Four",
                        "Classic 2-player connect four game",
                        "Connect 4 of your pieces in a row to win.",
                        "images/connect4.jpeg",
                        GameStatus.PUBLISHED,
                        "/games/connect4"        // internal React route
                ),
                new Game(
                        UUID.fromString("00000000-0000-0000-0000-000000000002"),
                        "Chess",
                        "External chess game provided by teachers",
                        "Standard chess rules.",
                        "images/chess.jpg",
                        GameStatus.PUBLISHED,
                        "http://localhost:3333"  // chess frontend
                )
        );
    }

    @Override
    public List<Game> loadPlayableGames() {

        return games.stream()
                .filter(Game::isPlayable)
                .toList();
    }
}

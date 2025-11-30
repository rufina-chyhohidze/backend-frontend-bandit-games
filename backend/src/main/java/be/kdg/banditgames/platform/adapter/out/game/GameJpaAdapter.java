package be.kdg.banditgames.platform.adapter.out.game;

import be.kdg.banditgames.common.shared.GameId;
import be.kdg.banditgames.platform.domain.Game;
import be.kdg.banditgames.platform.domain.GameStatus;
import be.kdg.banditgames.platform.port.out.LoadPlayableGamesPort;
import be.kdg.banditgames.platform.port.out.UpdateGamesPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional
public class GameJpaAdapter implements LoadPlayableGamesPort, UpdateGamesPort {
    private final GameJpaRepository jpa;

    public GameJpaAdapter(GameJpaRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public List<Game> loadPlayableGames() {
        return jpa.findByStatus(GameStatus.PUBLISHED).stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public Optional<Game> loadGameById(UUID gameId) {
        return jpa.findById(gameId)
                .map(this::toDomain);
    }

    private Game toDomain(GameJpaEntity e) {
        return new Game(
                GameId.of(e.getId()),
                e.getName(),
                e.getDescription(),
                e.getRules(),
                e.getPictureUrl(),
                e.getStatus(),
                e.getUrlGameSession()
        );
    }

    @Override
    public Game updateGames(Game game) {
        GameJpaEntity entity = new GameJpaEntity(
                game.getGameId().gameId(),
                game.getName(),
                game.getDescription(),
                game.getRules(),
                game.getPictureUrl(),
                game.getStatus(),
                game.getUrlGameSession()
        );
        jpa.save(entity);
        return game;
    }
}

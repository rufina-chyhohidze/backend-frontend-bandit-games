package be.kdg.banditgames.platform.adapter.out.game;

import be.kdg.banditgames.common.shared.GameId;
import be.kdg.banditgames.platform.domain.Game;
import be.kdg.banditgames.platform.domain.GameStatus;
import be.kdg.banditgames.platform.port.out.game.LoadDraftGamesPort;
import be.kdg.banditgames.platform.port.out.game.LoadGamesByIdsPort;
import be.kdg.banditgames.platform.port.out.game.LoadPlayableGamesPort;
import be.kdg.banditgames.platform.port.out.game.UpdateGamesPort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class GameJpaAdapter implements LoadPlayableGamesPort, LoadDraftGamesPort, UpdateGamesPort, LoadGamesByIdsPort {

    private final GameJpaRepository jpa;

    public GameJpaAdapter(GameJpaRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public List<Game> loadGamesByIds(List<UUID> ids) {
        return jpa.findByIdIn(ids)
                .stream()
                .map(this::toDomain)
                .toList();
    }
    @Override
    public List<Game> loadPlayableGames() {
        return jpa.findByStatus(GameStatus.PUBLISHED)
                .stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public Optional<Game> loadGameById(UUID gameId) {
        return jpa.findById(gameId)
                .map(this::toDomain);
    }


    @Override
        public Optional<Game> findById (GameId id){
            return jpa.findById(id.gameId())
                    .map(this::toDomain);
        }

        @Override
        public List<Game> findByStatusPendingApproval () {
            return jpa.findByStatus(GameStatus.DRAFT)
                    .stream()
                    .map(this::toDomain)
                    .toList();
        }

        @Override
        public Game updateGames (Game game){
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

        private Game toDomain (GameJpaEntity e){
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

    }

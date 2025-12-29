package be.kdg.banditgames.platform.adapter.out.game;

import be.kdg.banditgames.common.shared.GameId;
import be.kdg.banditgames.platform.domain.Game;
import be.kdg.banditgames.platform.domain.GameStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(GameJpaAdapter.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class GameJpaAdapterTest {

    @Autowired
    private GameJpaRepository jpaRepository;

    @Autowired
    private GameJpaAdapter adapter;

    @BeforeEach
    void setUp() {
        // Clean up any existing data before each test
        jpaRepository.deleteAll();
    }

    @Test
    @DisplayName("loadPlayableGames returns only games with status PUBLISHED")
    void loadPlayableGames_returnsPublishedOnly() {
        // given
        GameJpaEntity draft = new GameJpaEntity(
                UUID.randomUUID(),
                "DraftGame",
                "desc",
                "rules",
                "pic",
                GameStatus.DRAFT,
                "url"
        );
        GameJpaEntity published = new GameJpaEntity(
                UUID.randomUUID(),
                "PubGame",
                "desc",
                "rules",
                "pic",
                GameStatus.PUBLISHED,
                "url"
        );

        jpaRepository.saveAll(List.of(draft, published));

        // when
        List<Game> result = adapter.loadPlayableGames();

        // then
        assertThat(result)
                .hasSize(1)
                .first()
                .satisfies(game -> {
                    assertThat(game.getGameId().gameId()).isEqualTo(published.getId());
                    assertThat(game.getStatus()).isEqualTo(GameStatus.PUBLISHED);
                });
    }

    @Test
    @DisplayName("findByStatusPendingApproval returns only DRAFT games")
    void findByStatusPendingApproval_returnsDrafts() {
        // given
        GameJpaEntity draft = new GameJpaEntity(
                UUID.randomUUID(),
                "DraftGame",
                "desc",
                "rules",
                "pic",
                GameStatus.DRAFT,
                "url"
        );
        GameJpaEntity published = new GameJpaEntity(
                UUID.randomUUID(),
                "PubGame",
                "desc",
                "rules",
                "pic",
                GameStatus.PUBLISHED,
                "url"
        );
        jpaRepository.saveAll(List.of(draft, published));

        // when
        List<Game> result = adapter.findByStatusPendingApproval();

        // then
        assertThat(result)
                .hasSize(1)
                .first()
                .satisfies(game -> {
                    assertThat(game.getStatus()).isEqualTo(GameStatus.DRAFT);
                    assertThat(game.getGameId().gameId()).isEqualTo(draft.getId());
                });
    }

    @Test
    @DisplayName("findById loads and maps a game correctly")
    void findById_mapsCorrectly() {
        // given
        UUID id = UUID.randomUUID();
        GameJpaEntity entity = new GameJpaEntity(
                id,
                "Name",
                "Desc",
                "Rules",
                "pic",
                GameStatus.DRAFT,
                "url"
        );
        jpaRepository.save(entity);

        // when
        var resultOpt = adapter.findById(GameId.of(id));

        // then
        assertThat(resultOpt).isPresent();
        Game game = resultOpt.get();
        assertThat(game.getGameId().gameId()).isEqualTo(id);
        assertThat(game.getName()).isEqualTo("Name");
        assertThat(game.getDescription()).isEqualTo("Desc");
        assertThat(game.getRules()).isEqualTo("Rules");
        assertThat(game.getPictureUrl()).isEqualTo("pic");
        assertThat(game.getUrlGameSession()).isEqualTo("url");
        assertThat(game.getStatus()).isEqualTo(GameStatus.DRAFT);
    }

    @Test
    @DisplayName("updateGames persists status change")
    void updateGames_persistsStatusChange() {
        // given
        UUID id = UUID.randomUUID();
        GameJpaEntity entity = new GameJpaEntity(
                id,
                "Name",
                "Desc",
                "Rules",
                "pic",
                GameStatus.DRAFT,
                "url"
        );
        jpaRepository.save(entity);

        Game domainGame = new Game(
                GameId.of(id),
                "Name",
                "Desc",
                "Rules",
                "pic",
                GameStatus.DRAFT,
                "url"
        );

        // when: approve in domain & persist
        domainGame.acceptGame(); // changes to PUBLISHED
        adapter.updateGames(domainGame);

        // then
        GameJpaEntity reloaded = jpaRepository.findById(id).orElseThrow();
        assertThat(reloaded.getStatus()).isEqualTo(GameStatus.PUBLISHED);
    }
}

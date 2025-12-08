package be.kdg.banditgames.platform.core.player;

import be.kdg.banditgames.common.shared.GameId;
import be.kdg.banditgames.common.shared.PlayerId;
import be.kdg.banditgames.platform.domain.Game;
import be.kdg.banditgames.platform.domain.exception.player.PlayerNotFoundException;
import be.kdg.banditgames.platform.port.in.game.PlayableGameResult;
import be.kdg.banditgames.platform.port.in.player.ListFavoriteGamesCommand;
import be.kdg.banditgames.platform.port.out.game.LoadGamesByIdsPort;
import be.kdg.banditgames.platform.port.out.player.LoadPlayerPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListFavoriteGamesUseCaseImplTest {

    @Mock
    private LoadPlayerPort loadPlayerPort;

    @Mock
    private LoadGamesByIdsPort loadGamesByIdsPort;

    @InjectMocks
    private ListFavoriteGamesUseCaseImpl useCase;

    @Test
    void list_returnsPlayableGameResultsForFavoriteGames() {
        // given
        UUID playerUuid = UUID.randomUUID();
        PlayerId playerId = PlayerId.of(playerUuid);

        // mock Player domain
        var player = mock(be.kdg.banditgames.platform.domain.Player.class);

        GameId g1 = GameId.of(UUID.randomUUID());
        GameId g2 = GameId.of(UUID.randomUUID());

        when(loadPlayerPort.loadById(playerId)).thenReturn(Optional.of(player));
        when(player.getFavoriteGames()).thenReturn(List.of(g1, g2));

        Game game1 = new Game("G1", "d1", "r1", "pic1", "url1");
        Game game2 = new Game("G2", "d2", "r2", "pic2", "url2");

        when(loadGamesByIdsPort.loadGamesByIds(List.of(g1.gameId(), g2.gameId())))
                .thenReturn(List.of(game1, game2));

        ListFavoriteGamesCommand command = new ListFavoriteGamesCommand(playerId);

        // when
        List<PlayableGameResult> results = useCase.list(command);

        // then
        assertThat(results).hasSize(2);
        assertThat(results.get(0).name()).isEqualTo("G1");
        assertThat(results.get(0).gameId()).isEqualTo(game1.getGameId().gameId());
        assertThat(results.get(0).pictureUrl()).isEqualTo("pic1");
        assertThat(results.get(0).urlGameSession()).isEqualTo("url1");
    }

    @Test
    void list_throwsWhenPlayerNotFound() {
        // given
        PlayerId playerId = PlayerId.of(UUID.randomUUID());
        when(loadPlayerPort.loadById(playerId)).thenReturn(Optional.empty());

        ListFavoriteGamesCommand command = new ListFavoriteGamesCommand(playerId);

        // when / then
        assertThatThrownBy(() -> useCase.list(command))
                .isInstanceOf(PlayerNotFoundException.class);
        verifyNoInteractions(loadGamesByIdsPort);
    }
}

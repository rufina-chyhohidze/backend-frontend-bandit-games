package be.kdg.banditgames.platform.core.player;

import be.kdg.banditgames.common.shared.GameId;
import be.kdg.banditgames.common.shared.PlayerId;
import be.kdg.banditgames.platform.domain.Player;
import be.kdg.banditgames.platform.domain.exception.player.PlayerNotFoundException;
import be.kdg.banditgames.platform.port.in.player.RemoveFavoriteGameCommand;
import be.kdg.banditgames.platform.port.out.player.LoadPlayerPort;
import be.kdg.banditgames.platform.port.out.player.SavePlayerPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RemoveFavoriteGameUseCaseImplTest {

    @Mock
    private LoadPlayerPort loadPlayerPort;

    @Mock
    private SavePlayerPort savePlayerPort;

    @InjectMocks
    private RemoveFavoriteGameUseCaseImpl useCase;

    @Test
    void removeFromFavorites_loadsPlayer_callsRemoveAndSaves() {
        // given
        PlayerId playerId = PlayerId.of(UUID.randomUUID());
        GameId gameId = GameId.of(UUID.randomUUID());

        Player player = mock(Player.class);
        when(loadPlayerPort.loadById(playerId)).thenReturn(Optional.of(player));

        RemoveFavoriteGameCommand command = new RemoveFavoriteGameCommand(playerId, gameId);

        // when
        useCase.removeFromFavorites(command);

        // then
        verify(loadPlayerPort).loadById(playerId);
        verify(player).removeFavoriteGame(gameId);

        ArgumentCaptor<Player> playerCaptor = ArgumentCaptor.forClass(Player.class);
        verify(savePlayerPort).save(playerCaptor.capture());
        verifyNoMoreInteractions(loadPlayerPort, savePlayerPort);
    }

    @Test
    void removeFromFavorites_throwsWhenPlayerNotFound() {
        // given
        PlayerId playerId = PlayerId.of(UUID.randomUUID());
        GameId gameId = GameId.of(UUID.randomUUID());

        when(loadPlayerPort.loadById(playerId)).thenReturn(Optional.empty());

        RemoveFavoriteGameCommand command = new RemoveFavoriteGameCommand(playerId, gameId);

        // when / then
        assertThatThrownBy(() -> useCase.removeFromFavorites(command))
                .isInstanceOf(PlayerNotFoundException.class);

        verify(loadPlayerPort).loadById(playerId);
        verifyNoInteractions(savePlayerPort);
    }
}

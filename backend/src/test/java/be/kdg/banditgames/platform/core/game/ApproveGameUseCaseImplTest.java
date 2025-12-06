package be.kdg.banditgames.platform.core.game;

import be.kdg.banditgames.common.shared.GameId;
import be.kdg.banditgames.platform.domain.Game;
import be.kdg.banditgames.platform.domain.GameStatus;
import be.kdg.banditgames.platform.domain.exception.game.GameNotFoundException;
import be.kdg.banditgames.platform.port.in.game.ApproveGameCommand;
import be.kdg.banditgames.platform.port.out.game.LoadDraftGamesPort;
import be.kdg.banditgames.platform.port.out.game.UpdateGamesPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ApproveGameUseCaseImplTest {

    @Mock
    private LoadDraftGamesPort loadDraftGamesPort;

    @Mock
    private UpdateGamesPort updateGamesPort;

    @InjectMocks
    private ApproveGameUseCaseImpl approveGameUseCase;

    @Test
    void approveGame_changesStatusToPublished_andPersists() {
        // given
        UUID gameIdRaw = UUID.randomUUID();
        GameId gameId = GameId.of(gameIdRaw);

        Game draftGame = new Game(
                gameId,
                "Test game",
                "desc",
                "rules",
                "picUrl",
                GameStatus.DRAFT,
                "sessionUrl"
        );

        when(loadDraftGamesPort.findById(any(GameId.class)))
                .thenReturn(Optional.of(draftGame));

        // return same game instance from update port
        when(updateGamesPort.updateGames(any(Game.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // when
        ApproveGameCommand command = new ApproveGameCommand(gameIdRaw);
        Game result = approveGameUseCase.approveGame(command);

        // then
        assertThat(result.getStatus()).isEqualTo(GameStatus.PUBLISHED);

        // verify interactions with ports
        ArgumentCaptor<GameId> idCaptor = ArgumentCaptor.forClass(GameId.class);
        verify(loadDraftGamesPort).findById(idCaptor.capture());
        assertThat(idCaptor.getValue().gameId()).isEqualTo(gameIdRaw);

        verify(updateGamesPort).updateGames(draftGame);
        verifyNoMoreInteractions(loadDraftGamesPort, updateGamesPort);
    }

    @Test
    void approveGame_throwsWhenGameNotFound() {
        // given
        UUID gameIdRaw = UUID.randomUUID();
        when(loadDraftGamesPort.findById(any(GameId.class)))
                .thenReturn(Optional.empty());

        ApproveGameCommand command = new ApproveGameCommand(gameIdRaw);

        // when / then
        assertThatThrownBy(() -> approveGameUseCase.approveGame(command))
                .isInstanceOf(GameNotFoundException.class)
                .hasMessageContaining(gameIdRaw.toString());

        verify(loadDraftGamesPort).findById(any(GameId.class));
        verifyNoInteractions(updateGamesPort);
    }
}

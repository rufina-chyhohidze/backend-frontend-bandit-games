package be.kdg.banditgames.platform.core.game;

import be.kdg.banditgames.common.shared.GameId;
import be.kdg.banditgames.platform.domain.Game;
import be.kdg.banditgames.platform.domain.GameStatus;
import be.kdg.banditgames.platform.domain.exception.game.GameNotFoundException;
import be.kdg.banditgames.platform.port.in.game.RejectGameCommand;
import be.kdg.banditgames.platform.port.out.game.LoadDraftGamesPort;
import be.kdg.banditgames.platform.port.out.game.UpdateGamesPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RejectGameUseCaseImplTest {

    @Mock
    private LoadDraftGamesPort loadDraftGamesPort;

    @Mock
    private UpdateGamesPort updateGamesPort;

    private RejectGameUseCaseImpl rejectGameUseCase;

    @BeforeEach
    void setUp() {
        // Initialize with a list containing the mocked port
        rejectGameUseCase = new RejectGameUseCaseImpl(
                loadDraftGamesPort,
                List.of(updateGamesPort)
        );
    }

    @Test
    void rejectGame_changesStatusToRejected_andPersists() {
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

        // when
        RejectGameCommand command = new RejectGameCommand(gameIdRaw);
        Game result = rejectGameUseCase.rejectGame(command);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getStatus()).isEqualTo(GameStatus.REJECTED);

        // verify interactions with ports
        verify(loadDraftGamesPort).findById(any(GameId.class));
        verify(updateGamesPort).updateGames(draftGame);
        verifyNoMoreInteractions(loadDraftGamesPort, updateGamesPort);
    }

    @Test
    void rejectGame_throwsWhenGameNotFound() {
        // given
        UUID gameIdRaw = UUID.randomUUID();
        when(loadDraftGamesPort.findById(any(GameId.class)))
                .thenReturn(Optional.empty());

        RejectGameCommand command = new RejectGameCommand(gameIdRaw);

        // when / then
        assertThatThrownBy(() -> rejectGameUseCase.rejectGame(command))
                .isInstanceOf(GameNotFoundException.class);

        verify(loadDraftGamesPort).findById(any(GameId.class));
        verifyNoInteractions(updateGamesPort);
    }
}
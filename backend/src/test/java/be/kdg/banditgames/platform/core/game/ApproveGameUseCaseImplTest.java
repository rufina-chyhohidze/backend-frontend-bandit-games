package be.kdg.banditgames.platform.core.game;

import be.kdg.banditgames.common.shared.GameId;
import be.kdg.banditgames.platform.domain.Game;
import be.kdg.banditgames.platform.domain.GameStatus;
import be.kdg.banditgames.platform.domain.exception.game.GameNotFoundException;
import be.kdg.banditgames.platform.port.in.game.ApproveGameCommand;
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
class ApproveGameUseCaseImplTest {

    @Mock
    private LoadDraftGamesPort loadDraftGamesPort;

    @Mock
    private UpdateGamesPort updateGamesPort;

    private ApproveGameUseCaseImpl approveGameUseCase;

    @BeforeEach
    void setUp() {
        // Initialize with a list containing the mocked port
        approveGameUseCase = new ApproveGameUseCaseImpl(
                loadDraftGamesPort,
                List.of(updateGamesPort)
        );
    }

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

        // when
        ApproveGameCommand command = new ApproveGameCommand(gameIdRaw);
        Game result = approveGameUseCase.approveGame(command);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getStatus()).isEqualTo(GameStatus.PUBLISHED);

        // verify interactions with ports
        verify(loadDraftGamesPort).findById(any(GameId.class));
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
                .isInstanceOf(GameNotFoundException.class);

        verify(loadDraftGamesPort).findById(any(GameId.class));
        verifyNoInteractions(updateGamesPort);
    }
}
package be.kdg.banditgames.platform.core.game;

import be.kdg.banditgames.platform.domain.Game;
import be.kdg.banditgames.platform.port.in.game.GameSubmissionCommand;
import be.kdg.banditgames.platform.port.out.game.UpdateGamesPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SubmitGameToPlatformUseCaseImplTest {

    @Mock
    private UpdateGamesPort updateGamesPort;

    @InjectMocks
    private SubmitGameToPlatformUseCaseImpl useCase;

    @Test
    void submitGame_createsDraftGameAndPersists() {
        // given
        GameSubmissionCommand command = new GameSubmissionCommand(
                "Chess",
                "Classic game",
                "rules",
                "pic-url",
                "session-url"
        );

        when(updateGamesPort.updateGames(any(Game.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // when
        Game result = useCase.submitGame(command);

        // then:
        assertThat(result.getName()).isEqualTo("Chess");
        assertThat(result.getDescription()).isEqualTo("Classic game");
        assertThat(result.getRules()).isEqualTo("rules");
        assertThat(result.getPictureUrl()).isEqualTo("pic-url");
        assertThat(result.getUrlGameSession()).isEqualTo("session-url");
        assertThat(result.getGameId()).isNotNull(); // constructor generates UUID

        ArgumentCaptor<Game> gameCaptor = ArgumentCaptor.forClass(Game.class);
        verify(updateGamesPort).updateGames(gameCaptor.capture());
        Game persisted = gameCaptor.getValue();
        assertThat(persisted.getName()).isEqualTo("Chess");

        verifyNoMoreInteractions(updateGamesPort);
    }
}

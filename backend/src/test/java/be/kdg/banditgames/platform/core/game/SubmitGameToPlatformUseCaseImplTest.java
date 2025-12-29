package be.kdg.banditgames.platform.core.game;

import be.kdg.banditgames.common.events.generic.GenericAchievementDto;
import be.kdg.banditgames.platform.domain.Achievement;
import be.kdg.banditgames.platform.domain.Game;
import be.kdg.banditgames.platform.port.in.game.GameSubmissionCommand;
import be.kdg.banditgames.platform.port.out.achievement.UpdateAchievementsPort;
import be.kdg.banditgames.platform.port.out.game.UpdateGamesPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SubmitGameToPlatformUseCaseImplTest {

    @Mock
    private UpdateGamesPort updateGamesPort;

    @Mock
    private UpdateAchievementsPort updateAchievementsPort;

    private SubmitGameToPlatformUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        // Since the service takes a List, we wrap our mock in a List
        useCase = new SubmitGameToPlatformUseCaseImpl(
                List.of(updateGamesPort),
                updateAchievementsPort
        );
    }

    @Test
    void submitGame_createsDraftGameAndPersistsAchievements() {
        // given
        GenericAchievementDto achievementDto = new GenericAchievementDto(
                "WIN_1",
                "Win 1 game"
        );

        GameSubmissionCommand command = new GameSubmissionCommand(
                "Chess",
                "Classic game",
                "rules",
                "pic-url",
                "session-url",
                List.of(achievementDto) // Assuming your record has this field
        );

        // when
        Game result = useCase.submitGame(command);

        // then: Validate Game Mapping
        assertThat(result.getName()).isEqualTo("Chess");
        assertThat(result.getGameId()).isNotNull();

        // Verify Game Port was called
        verify(updateGamesPort).updateGames(any(Game.class));

        // Verify Achievement Port was called
        ArgumentCaptor<Achievement> achievementCaptor = ArgumentCaptor.forClass(Achievement.class);
        verify(updateAchievementsPort).save(achievementCaptor.capture());

        Achievement savedAchievement = achievementCaptor.getValue();
        assertThat(savedAchievement.getGameId()).isEqualTo(result.getGameId());
        assertThat(savedAchievement.getName()).isEqualTo("WIN_1");
        assertThat(savedAchievement.getDescription()).isEqualTo("Win 1 game");

        verifyNoMoreInteractions(updateGamesPort, updateAchievementsPort);
    }
}
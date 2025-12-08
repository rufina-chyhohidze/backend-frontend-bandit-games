package be.kdg.banditgames.platform.adapter.in.achievement;

import be.kdg.banditgames.platform.adapter.in.AchievementController;
import be.kdg.banditgames.platform.port.in.achievement.AvailableAchievementResult;
import be.kdg.banditgames.platform.port.in.achievement.ListAvailableAchievementsCommand;
import be.kdg.banditgames.platform.port.in.achievement.ListAvailableAchievementsUseCase;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AchievementController.class)
@Import(AchievementControllerTest.MockConfig.class)
class AchievementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ListAvailableAchievementsUseCase listAvailableAchievementsUseCase;

    @TestConfiguration
    static class MockConfig {
        @Bean
        ListAvailableAchievementsUseCase listAvailableAchievementsUseCase() {
            return mock(ListAvailableAchievementsUseCase.class);
        }
    }

    @Test
    void getAvailableAchievements_callsUseCaseWithCorrectGameId() throws Exception {
        UUID gameUuid = UUID.randomUUID();

        AvailableAchievementResult result = mock(AvailableAchievementResult.class);
        when(listAvailableAchievementsUseCase.listAvailableAchievements(any()))
                .thenReturn(List.of(result));

        mockMvc.perform(
                        get("/api/games/{gameId}/achievements", gameUuid)
                                .with(jwt())
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());

        ArgumentCaptor<ListAvailableAchievementsCommand> captor =
                ArgumentCaptor.forClass(ListAvailableAchievementsCommand.class);
        verify(listAvailableAchievementsUseCase).listAvailableAchievements(captor.capture());

        ListAvailableAchievementsCommand cmd = captor.getValue();
        assertThat(cmd.gameId().gameId()).isEqualTo(gameUuid);
    }
}

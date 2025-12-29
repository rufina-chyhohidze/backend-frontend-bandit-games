package be.kdg.banditgames.platform.adapter.in.game;

import be.kdg.banditgames.common.shared.GameId;
import be.kdg.banditgames.platform.adapter.in.AdminGameModerationController;
import be.kdg.banditgames.platform.domain.Game;
import be.kdg.banditgames.platform.domain.GameStatus;
import be.kdg.banditgames.platform.port.in.game.ApproveGameCommand;
import be.kdg.banditgames.platform.port.in.game.ApproveGameUseCase;
import be.kdg.banditgames.platform.port.in.game.ListPendingGamesUseCase;
import be.kdg.banditgames.platform.port.in.game.RejectGameUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import be.kdg.banditgames.platform.port.in.game.RejectGameCommand;


import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdminGameModerationController.class)
@Import(AdminGameModerationControllerTest.MockConfig.class)
class AdminGameModerationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ApproveGameUseCase approveGameUseCase;

    @Autowired
    private RejectGameUseCase rejectGameUseCase;

    @Autowired
    private ListPendingGamesUseCase listPendingGamesUseCase;

    @TestConfiguration
    static class MockConfig {
        @Bean
        ApproveGameUseCase approveGameUseCase() {
            return mock(ApproveGameUseCase.class);
        }

        @Bean
        RejectGameUseCase rejectGameUseCase() {
            return mock(RejectGameUseCase.class);
        }

        @Bean
        ListPendingGamesUseCase listPendingGamesUseCase() {
            return mock(ListPendingGamesUseCase.class);
        }
    }

    @Test
    @WithMockUser(authorities = "admin")
    void approve_returnsApprovedGameDto() throws Exception {
        // given
        UUID id = UUID.randomUUID();
        Game approved = new Game(
                GameId.of(id),
                "Chess",
                "Classic game",
                "rules",
                "pic",
                GameStatus.PUBLISHED,
                "url"
        );

        when(approveGameUseCase.approveGame(any(ApproveGameCommand.class)))
                .thenReturn(approved);

        // when / then
        mockMvc.perform(
                        post("/api/admin/games/{gameId}/approve", id)
                                .with(csrf())
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.gameId").value(id.toString()))
                .andExpect(jsonPath("$.name").value("Chess"))
                .andExpect(jsonPath("$.status").value("PUBLISHED"));
    }

    @Test
    @WithMockUser(authorities = "admin")
    void listPendingGames_returnsListOfDtos() throws Exception {
        // given
        Game g1 = new Game("Game1", "d1", "r1", "pic1", "url1");
        Game g2 = new Game("Game2", "d2", "r2", "pic2", "url2");

        when(listPendingGamesUseCase.listPendingGames())
                .thenReturn(List.of(g1, g2));

        // when / then
        mockMvc.perform(get("/api/admin/games/pending"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Game1"))
                .andExpect(jsonPath("$[1].name").value("Game2"));
    }

    @Test
    void approve_requiresAuthentication() throws Exception {
        UUID id = UUID.randomUUID();

        mockMvc.perform(
                        post("/api/admin/games/{gameId}/approve", id)
                                .with(csrf())
                )
                .andExpect(status().isUnauthorized());
    }
    @Test
    @WithMockUser(authorities = "admin")
    void reject_returnsRejectedGameDto() throws Exception {
        // given
        UUID id = UUID.randomUUID();
        Game rejected = new Game(
                GameId.of(id),
                "Checkers",
                "Board game",
                "rules",
                "pic",
                GameStatus.REJECTED,
                "url"
        );

        when(rejectGameUseCase.rejectGame(any(RejectGameCommand.class)))
                .thenReturn(rejected);

        // when / then
        mockMvc.perform(
                        post("/api/admin/games/{gameId}/reject", id)
                                .with(csrf())
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.gameId").value(id.toString()))
                .andExpect(jsonPath("$.name").value("Checkers"))
                .andExpect(jsonPath("$.status").value("REJECTED"));
    }

}

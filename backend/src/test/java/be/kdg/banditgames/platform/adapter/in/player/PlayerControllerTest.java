package be.kdg.banditgames.platform.adapter.in.player;

import be.kdg.banditgames.common.shared.PlayerId;
import be.kdg.banditgames.platform.adapter.in.PlayerController;
import be.kdg.banditgames.platform.domain.Player;
import be.kdg.banditgames.platform.port.in.game.PlayableGameResult;
import be.kdg.banditgames.platform.port.in.player.*;
import be.kdg.banditgames.platform.port.in.achievement.AwardAchievementUseCase;
import be.kdg.banditgames.platform.port.in.achievement.ListUnlockedAchievementsUseCase; // Added import
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PlayerController.class)
class PlayerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PlayerCreationUseCase playerCreationUseCase;

    @MockitoBean
    private FindPlayerPort findPlayerPort;

    @MockitoBean
    private AddFavoriteGameUseCase addFavoriteGameUseCase;

    @MockitoBean
    private RemoveFavoriteGameUseCase removeFavoriteGameUseCase;

    @MockitoBean
    private ListFavoriteGamesUseCase listFavoriteGamesUseCase;

    @MockitoBean
    private AwardAchievementUseCase awardAchievementUseCase;

    @MockitoBean
    private ListUnlockedAchievementsUseCase listUnlockedAchievementsUseCase; // FIX: Added missing dependency

    @MockitoBean
    private JwtDecoder jwtDecoder; // Required for @WebMvcTest with OAuth2

    private Player dummyPlayer(UUID id) {
        Player player = mock(Player.class);
        when(player.getPlayerId()).thenReturn(PlayerId.of(id));
        when(player.getUsername()).thenReturn("rufina");
        when(player.getFavoriteGames()).thenReturn(List.of());
        return player;
    }

    @Test
    @WithMockUser(authorities = "player")
    void addFavorite_addsFavoriteAndReturnsDto() throws Exception {
        UUID subjectId = UUID.randomUUID();
        UUID gameId = UUID.randomUUID();

        Player updated = dummyPlayer(subjectId);
        when(addFavoriteGameUseCase.addToFavorites(any(AddFavoriteGameCommand.class)))
                .thenReturn(updated);

        mockMvc.perform(
                        post("/api/player/favorites/{gameId}", gameId)
                                .with(csrf())
                                .with(jwt().jwt(jwt -> {
                                    jwt.subject(subjectId.toString());
                                    jwt.claim("preferred_username", "rufina");
                                }))
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id").value(subjectId.toString()))
                .andExpect(jsonPath("$.username").value("rufina"));
    }

    @Test
    @WithMockUser(authorities = "player")
    void removeFavorite_removesFavoriteAndReturnsDto() throws Exception {
        UUID subjectId = UUID.randomUUID();
        UUID gameId = UUID.randomUUID();

        Player updated = dummyPlayer(subjectId);
        when(removeFavoriteGameUseCase.removeFromFavorites(any(RemoveFavoriteGameCommand.class)))
                .thenReturn(updated);

        mockMvc.perform(
                        delete("/api/player/favorites/{gameId}", gameId)
                                .with(csrf())
                                .with(jwt().jwt(jwt -> {
                                    jwt.subject(subjectId.toString());
                                    jwt.claim("preferred_username", "rufina");
                                }))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(subjectId.toString()));
    }

    @Test
    @WithMockUser(authorities = "player")
    void favorites_returnsPlayableGames() throws Exception {
        UUID subjectId = UUID.randomUUID();

        PlayableGameResult r1 = new PlayableGameResult(
                UUID.randomUUID(),
                "Chess",
                "desc",
                "pic",
                "url"
        );

        when(listFavoriteGamesUseCase.list(any(ListFavoriteGamesCommand.class)))
                .thenReturn(List.of(r1));

        mockMvc.perform(
                        get("/api/player/favorites")
                                .with(jwt().jwt(jwt -> {
                                    jwt.subject(subjectId.toString());
                                    jwt.claim("preferred_username", "rufina");
                                }))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("Chess"));
    }
}
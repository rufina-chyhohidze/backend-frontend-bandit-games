package be.kdg.banditgames.platform.adapter.in.lobby;


import be.kdg.banditgames.common.shared.GameId;
import be.kdg.banditgames.common.shared.PlayerId;
import be.kdg.banditgames.common.shared.PlayerType;
import be.kdg.banditgames.platform.adapter.in.LobbyController;
import be.kdg.banditgames.platform.adapter.in.response.StartGameResponse;
import be.kdg.banditgames.platform.domain.Lobby;
import be.kdg.banditgames.platform.domain.LobbyStatus;
import be.kdg.banditgames.platform.domain.vo.LobbyId;
import be.kdg.banditgames.platform.port.in.lobby.CreateLobbyCommand;
import be.kdg.banditgames.platform.port.in.lobby.FindLobbyPort;
import be.kdg.banditgames.platform.port.in.lobby.LobbyCreationUseCase;
import be.kdg.banditgames.platform.port.in.lobby.ManagingLobbyUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LobbyController.class)
@Import(LobbyControllerTest.MockConfig.class)
class LobbyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LobbyCreationUseCase lobbyCreationUseCase;

    @Autowired
    private ManagingLobbyUseCase managingLobbyUseCase;

    @Autowired
    private FindLobbyPort findLobbyPort;

    @TestConfiguration
    static class MockConfig {
        @Bean
        LobbyCreationUseCase lobbyCreationUseCase() {
            return mock(LobbyCreationUseCase.class);
        }

        @Bean
        ManagingLobbyUseCase managingLobbyUseCase() {
            return mock(ManagingLobbyUseCase.class);
        }

        @Bean
        FindLobbyPort findLobbyPort() {
            return mock(FindLobbyPort.class);
        }
    }

    private Lobby dummyLobby(UUID lobbyUuid, UUID hostId, UUID guestId, UUID gameId) {
        Lobby lobby = mock(Lobby.class);
        when(lobby.getLobbyId()).thenReturn(LobbyId.of(lobbyUuid));
        when(lobby.getHostPlayer()).thenReturn(PlayerId.of(hostId));
        when(lobby.getHostType()).thenReturn(PlayerType.HUMAN);
        when(lobby.getGuestPlayer()).thenReturn(PlayerId.of(guestId));
        when(lobby.getGuestType()).thenReturn(PlayerType.HUMAN);
        when(lobby.getLobbyStatus()).thenReturn(LobbyStatus.IN_GAME);
        when(lobby.getGameId()).thenReturn(GameId.of(gameId));
        return lobby;
    }

    @Test
    void getAllLobbies_returnsLobbyDtos() throws Exception {
        UUID lobbyId = UUID.randomUUID();
        Lobby lobby = dummyLobby(lobbyId, UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID());

        when(findLobbyPort.findLobbies()).thenReturn(List.of(lobby));

        mockMvc.perform(
                        get("/api/lobby/all")
                                .with(jwt())
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].lobbyId").value(lobbyId.toString()));
    }

    @Test
    void createLobby_usesJwtSubject_andReturnsDto() throws Exception {
        UUID playerId = UUID.randomUUID();
        UUID lobbyId = UUID.randomUUID();
        Lobby lobby = dummyLobby(lobbyId, playerId, UUID.randomUUID(), UUID.randomUUID());

        when(lobbyCreationUseCase.createLobby(any(CreateLobbyCommand.class)))
                .thenReturn(lobby);

        mockMvc.perform(
                        post("/api/lobby/create")
                                .with(csrf())
                                .with(jwt().jwt(jwt -> jwt.subject(playerId.toString())))
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{}")
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.lobbyId").value(lobbyId.toString()));
    }

    @Test
    void startGame_returnsStartGameResponse() throws Exception {
        UUID lobbyId = UUID.randomUUID();

        StartGameResponse response = new StartGameResponse(
                "http://host-url",
                "http://guest-url",
                "HUMAN",
                "AI_EASY"
        );

        when(managingLobbyUseCase.startGameInLobby(LobbyId.of(lobbyId)))
                .thenReturn(response);

        mockMvc.perform(
                        post("/api/lobby/{lobbyId}/start-game", lobbyId)
                                .with(csrf())
                                .with(jwt())
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.hostUrl").value("http://host-url"))
                .andExpect(jsonPath("$.guestUrl").value("http://guest-url"));
    }

    @Test
    void chooseAiOpponent_mapsDifficultyAndReturnsUpdatedLobby() throws Exception {
        UUID lobbyId = UUID.randomUUID();
        UUID playerId = UUID.randomUUID();
        UUID gameId = UUID.randomUUID();

        Lobby lobby = dummyLobby(lobbyId, playerId, null, gameId);
        when(findLobbyPort.findLobbyById(lobbyId)).thenReturn(lobby);

        // managingLobbyUseCase.chooseAiOpponent returns void
        doNothing().when(managingLobbyUseCase).chooseAiOpponent(
                LobbyId.of(lobbyId),
                PlayerId.of(playerId),
                PlayerType.AI_EASY
        );

        mockMvc.perform(
                        post("/api/lobby/{lobbyId}/choose-ai", lobbyId)
                                .with(csrf())
                                .with(jwt().jwt(jwt -> jwt.subject(playerId.toString())))
                                .param("difficulty", "EASY")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lobbyId").value(lobbyId.toString()));

        verify(managingLobbyUseCase)
                .chooseAiOpponent(LobbyId.of(lobbyId), PlayerId.of(playerId), PlayerType.AI_EASY);
    }
}

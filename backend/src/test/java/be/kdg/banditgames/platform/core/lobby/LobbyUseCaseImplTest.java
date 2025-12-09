package be.kdg.banditgames.platform.core.lobby;

import be.kdg.banditgames.common.shared.GameId;
import be.kdg.banditgames.common.shared.PlayerId;
import be.kdg.banditgames.common.shared.PlayerType;
import be.kdg.banditgames.platform.adapter.in.response.StartGameResponse;
import be.kdg.banditgames.platform.domain.Game;
import be.kdg.banditgames.platform.domain.GameStatus;
import be.kdg.banditgames.platform.domain.Lobby;
import be.kdg.banditgames.platform.domain.exception.lobby.PlayerAlreadyInLobbyException;
import be.kdg.banditgames.platform.domain.vo.LobbyId;
import be.kdg.banditgames.platform.port.in.lobby.CreateGameCommand;
import be.kdg.banditgames.platform.port.in.lobby.CreateLobbyCommand;
import be.kdg.banditgames.platform.port.out.game.LoadPlayableGamesPort;
import be.kdg.banditgames.platform.port.out.lobby.CreateGameService;
import be.kdg.banditgames.platform.port.out.lobby.LoadLobbyPort;
import be.kdg.banditgames.platform.port.out.lobby.LobbyLookupPort;
import be.kdg.banditgames.platform.port.out.lobby.PersistLobbyPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LobbyUseCaseImplTest {

    @Mock
    private LoadLobbyPort loadLobbyPort;

    @Mock
    private PersistLobbyPort persistLobbyPort;

    @Mock
    private LobbyLookupPort lobbyLookupPort;

    @Mock
    private LoadPlayableGamesPort loadPlayableGamesPort;

    @Mock
    private CreateGameService createGameService;

    @InjectMocks
    private LobbyUseCaseImpl lobbyUseCase;

    @Test
    void createLobby_whenPlayerNotInAnyLobby_persistsLobby() {
        UUID playerUuid = UUID.randomUUID();
        CreateLobbyCommand command = new CreateLobbyCommand(playerUuid);

        when(lobbyLookupPort.isPlayerInAnyLobby(PlayerId.of(playerUuid)))
                .thenReturn(false);

        Lobby lobby = lobbyUseCase.createLobby(command);

        assertThat(lobby).isNotNull();
        verify(lobbyLookupPort).isPlayerInAnyLobby(PlayerId.of(playerUuid));
        verify(persistLobbyPort).saveLobby(any(Lobby.class));
    }

    @Test
    void createLobby_whenPlayerAlreadyInLobby_throws() {
        UUID playerUuid = UUID.randomUUID();
        CreateLobbyCommand command = new CreateLobbyCommand(playerUuid);

        when(lobbyLookupPort.isPlayerInAnyLobby(PlayerId.of(playerUuid)))
                .thenReturn(true);

        assertThatThrownBy(() -> lobbyUseCase.createLobby(command))
                .isInstanceOf(PlayerAlreadyInLobbyException.class);

        verify(persistLobbyPort, never()).saveLobby(any());
    }

    @Test
    void addPlayerToLobby_whenFree_addsGuestAndPersists() {
        PlayerId playerId = PlayerId.of(UUID.randomUUID());
        LobbyId lobbyId = LobbyId.of(UUID.randomUUID());

        when(lobbyLookupPort.isPlayerInAnyLobby(playerId)).thenReturn(false);

        Lobby lobby = mock(Lobby.class);
        when(loadLobbyPort.loadLobbyById(lobbyId)).thenReturn(Optional.of(lobby));

        Lobby result = lobbyUseCase.addPlayerToLobby(playerId, lobbyId);

        verify(lobby).changeGuest(playerId, PlayerType.HUMAN);
        verify(persistLobbyPort).saveLobby(lobby);
        assertThat(result).isSameAs(lobby);
    }

    @Test
    void addPlayerToLobby_whenPlayerAlreadyInLobby_throws() {
        PlayerId playerId = PlayerId.of(UUID.randomUUID());
        LobbyId lobbyId = LobbyId.of(UUID.randomUUID());

        when(lobbyLookupPort.isPlayerInAnyLobby(playerId)).thenReturn(true);

        assertThatThrownBy(() -> lobbyUseCase.addPlayerToLobby(playerId, lobbyId))
                .isInstanceOf(PlayerAlreadyInLobbyException.class);

        verify(persistLobbyPort, never()).saveLobby(any());
    }

    @Test
    void removePlayerFromLobby_removesAndPersists() {
        PlayerId playerId = PlayerId.of(UUID.randomUUID());
        LobbyId lobbyId = LobbyId.of(UUID.randomUUID());

        Lobby lobby = mock(Lobby.class);
        when(loadLobbyPort.loadLobbyById(lobbyId)).thenReturn(Optional.of(lobby));

        lobbyUseCase.removePlayerFromLobby(playerId, lobbyId);

        verify(lobby).removePlayer(playerId);
        verify(persistLobbyPort).saveLobby(lobby);
    }

    @Test
    void startGameInLobby_whenNotStarted_createsGameAndBuildsUrls() {
        LobbyId lobbyId = LobbyId.of(UUID.randomUUID());
        PlayerId hostId = PlayerId.of(UUID.randomUUID());
        PlayerId guestId = PlayerId.of(UUID.randomUUID());
        GameId gameId = GameId.of(UUID.randomUUID());

        Lobby lobby = mock(Lobby.class);
        when(loadLobbyPort.loadLobbyById(lobbyId)).thenReturn(Optional.of(lobby));
        when(lobby.getGameId()).thenReturn(gameId);
        when(lobby.hasStartedGame()).thenReturn(false);
        when(lobby.getHostPlayer()).thenReturn(hostId);
        when(lobby.getGuestPlayer()).thenReturn(guestId);
        when(lobby.getHostType()).thenReturn(PlayerType.HUMAN);
        when(lobby.getGuestType()).thenReturn(PlayerType.HUMAN);

        Game game = new Game(
                gameId,
                "Chess",
                "desc",
                "rules",
                "pic",
                GameStatus.PUBLISHED,
                "http://game-url"
        );
        when(loadPlayableGamesPort.loadGameById(gameId.gameId()))
                .thenReturn(Optional.of(game));

        StartGameResponse response = lobbyUseCase.startGameInLobby(lobbyId);

        ArgumentCaptor<CreateGameCommand> cmdCaptor = ArgumentCaptor.forClass(CreateGameCommand.class);
        verify(createGameService).createGameForLobby(cmdCaptor.capture());
        CreateGameCommand cmd = cmdCaptor.getValue();
        assertThat(cmd.sessionId()).isEqualTo(lobbyId.lobbyID());
        assertThat(cmd.player1Id()).isEqualTo(hostId.playerId());
        assertThat(cmd.player2Id()).isEqualTo(guestId.playerId());
        assertThat(cmd.player1Type()).isEqualTo(PlayerType.HUMAN);
        assertThat(cmd.player2Type()).isEqualTo(PlayerType.HUMAN);

        verify(lobby).startGame();
        verify(persistLobbyPort).saveLobby(lobby);

        assertThat(response.hostUrl())
                .startsWith("http://game-url")
                .contains("sessionId=" + lobbyId.lobbyID())
                .contains("playerId=" + hostId.playerId());
        assertThat(response.guestUrl())
                .startsWith("http://game-url")
                .contains("sessionId=" + lobbyId.lobbyID())
                .contains("playerId=" + guestId.playerId());
    }

    @Test
    void startGameInLobby_whenAlreadyStarted_doesNotCreateGameAgain() {
        LobbyId lobbyId = LobbyId.of(UUID.randomUUID());
        GameId gameId = GameId.of(UUID.randomUUID());
        PlayerId hostId = PlayerId.of(UUID.randomUUID());

        Lobby lobby = mock(Lobby.class);
        when(loadLobbyPort.loadLobbyById(lobbyId)).thenReturn(Optional.of(lobby));
        when(lobby.getGameId()).thenReturn(gameId);
        when(lobby.hasStartedGame()).thenReturn(true);
        when(lobby.getHostPlayer()).thenReturn(hostId);
        when(lobby.getHostType()).thenReturn(PlayerType.HUMAN);
        when(lobby.getGuestType()).thenReturn(PlayerType.AI_EASY);
        when(lobby.getGuestPlayer()).thenReturn(null);

        Game game = new Game(
                gameId,
                "Chess",
                "desc",
                "rules",
                "pic",
                GameStatus.PUBLISHED,
                "http://game-url"
        );
        when(loadPlayableGamesPort.loadGameById(gameId.gameId()))
                .thenReturn(Optional.of(game));

        StartGameResponse response = lobbyUseCase.startGameInLobby(lobbyId);
        verify(createGameService, never()).createGameForLobby(any());
        verify(lobby, never()).startGame();
        verify(persistLobbyPort, never()).saveLobby(any());
        assertThat(response.guestUrl()).contains("playerId=AI");
    }



    @Test
    void chooseGameForLobby_setsGameAndPersists() {
        LobbyId lobbyId = LobbyId.of(UUID.randomUUID());
        GameId gameId = GameId.of(UUID.randomUUID());
        Lobby lobby = mock(Lobby.class);
        when(loadLobbyPort.loadLobbyById(lobbyId)).thenReturn(Optional.of(lobby));

        lobbyUseCase.chooseGameForLobby(lobbyId, gameId);

        verify(lobby).chooseGame(gameId);
        verify(persistLobbyPort).saveLobby(lobby);
    }

    @Test
    void chooseAiOpponent_whenHostAndNoHumanGuest_setsAiAndPersists() {
        LobbyId lobbyId = LobbyId.of(UUID.randomUUID());
        PlayerId hostId = PlayerId.of(UUID.randomUUID());

        Lobby lobby = mock(Lobby.class);
        when(loadLobbyPort.loadLobbyById(lobbyId)).thenReturn(Optional.of(lobby));
        when(lobby.getHostPlayer()).thenReturn(hostId);
        when(lobby.getGuestPlayer()).thenReturn(null); // enough

        lobbyUseCase.chooseAiOpponent(lobbyId, hostId, PlayerType.AI_HARD);

        verify(lobby).changeGuestToAI(PlayerType.AI_HARD);
        verify(persistLobbyPort).saveLobby(lobby);
    }


    @Test
    void chooseAiOpponent_whenNotHost_throws() {
        LobbyId lobbyId = LobbyId.of(UUID.randomUUID());
        PlayerId hostId = PlayerId.of(UUID.randomUUID());
        PlayerId other = PlayerId.of(UUID.randomUUID());

        Lobby lobby = mock(Lobby.class);
        when(loadLobbyPort.loadLobbyById(lobbyId)).thenReturn(Optional.of(lobby));
        when(lobby.getHostPlayer()).thenReturn(hostId);

        assertThatThrownBy(() ->
                lobbyUseCase.chooseAiOpponent(lobbyId, other, PlayerType.AI_EASY)
        ).isInstanceOf(IllegalStateException.class);

        verify(persistLobbyPort, never()).saveLobby(any());
    }

    @Test
    void chooseAiOpponent_whenHumanGuestPresent_throws() {
        LobbyId lobbyId = LobbyId.of(UUID.randomUUID());
        PlayerId hostId = PlayerId.of(UUID.randomUUID());
        PlayerId guestId = PlayerId.of(UUID.randomUUID());

        Lobby lobby = mock(Lobby.class);
        when(loadLobbyPort.loadLobbyById(lobbyId)).thenReturn(Optional.of(lobby));
        when(lobby.getHostPlayer()).thenReturn(hostId);
        when(lobby.getGuestPlayer()).thenReturn(guestId);
        when(lobby.getGuestType()).thenReturn(PlayerType.HUMAN);

        assertThatThrownBy(() ->
                lobbyUseCase.chooseAiOpponent(lobbyId, hostId, PlayerType.AI_EASY)
        ).isInstanceOf(IllegalStateException.class);

        verify(persistLobbyPort, never()).saveLobby(any());
    }
}

package be.kdg.banditgames.platform.adapter.out.lobby;

import be.kdg.banditgames.common.shared.GameId;
import be.kdg.banditgames.common.shared.PlayerId;
import be.kdg.banditgames.common.shared.PlayerType;
import be.kdg.banditgames.platform.domain.Lobby;
import be.kdg.banditgames.platform.domain.LobbyStatus;
import be.kdg.banditgames.platform.domain.vo.LobbyId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LobbyJpaAdapterTest {

    @Mock
    LobbyJpaRepository repo;

    @InjectMocks
    LobbyJpaAdapter adapter;

    @Test
    void loadLobbyById_mapsEntityToDomainCorrectly() {
        UUID lobbyUuid = UUID.randomUUID();
        UUID hostUuid = UUID.randomUUID();
        UUID gameUuid = UUID.randomUUID();

        LobbyJpaEntity entity = new LobbyJpaEntity();
        entity.setId(lobbyUuid);
        entity.setHostPlayerId(hostUuid);
        entity.setHostType(PlayerType.HUMAN);
        entity.setStatus(LobbyStatus.WAITING);
        entity.setGameId(gameUuid);
        when(repo.findById(lobbyUuid)).thenReturn(Optional.of(entity));

        Optional<Lobby> result = adapter.loadLobbyById(LobbyId.of(lobbyUuid));

        assertThat(result).isPresent();
        Lobby lobby = result.get();
        assertThat(lobby.getLobbyId().lobbyID()).isEqualTo(lobbyUuid);
        assertThat(lobby.getHostPlayer().playerId()).isEqualTo(hostUuid);
        assertThat(lobby.getLobbyStatus()).isEqualTo(LobbyStatus.WAITING);
        assertThat(lobby.getGameId()).isNotNull();
        assertThat(lobby.getGameId().gameId()).isEqualTo(gameUuid);
    }

    @Test
    void saveLobby_mapsDomainToEntityAndCallsRepository() {
        UUID lobbyUuid = UUID.randomUUID();
        UUID hostUuid = UUID.randomUUID();
        GameId gameId = GameId.of(UUID.randomUUID());

        Lobby lobby = Lobby.rehydrate(
                LobbyId.of(lobbyUuid),
                PlayerId.of(hostUuid),
                PlayerType.HUMAN,
                null,
                null,
                LobbyStatus.WAITING,
                gameId
        );

        adapter.saveLobby(lobby);

        verify(repo).save(argThat(entity ->
                entity.getId().equals(lobbyUuid)
                        && entity.getHostPlayerId().equals(hostUuid)
                        && entity.getStatus() == LobbyStatus.WAITING
                        && entity.getGameId().equals(gameId.gameId())
        ));
    }

    @Test
    void removeLobby_deletesById() {
        UUID lobbyId = UUID.randomUUID();

        adapter.removeLobby(LobbyId.of(lobbyId));

        verify(repo).deleteById(lobbyId);
    }

    @Test
    void isPlayerInAnyLobby_delegatesToRepository() {
        UUID pid = UUID.randomUUID();

        when(repo.existsByHostPlayerIdOrGuestPlayerId(pid, pid)).thenReturn(true);

        boolean result = adapter.isPlayerInAnyLobby(PlayerId.of(pid));

        assertThat(result).isTrue();
        verify(repo).existsByHostPlayerIdOrGuestPlayerId(pid, pid);
    }

    @Test
    void loadLobbyByPlayerId_delegatesToRepository() {
        UUID pid = UUID.randomUUID();

        when(repo.findByHostPlayerIdOrGuestPlayerId(pid, pid)).thenReturn(Optional.empty());

        Optional<Lobby> result = adapter.loadLobbyByPlayerId(PlayerId.of(pid));

        assertThat(result).isEmpty();
        verify(repo).findByHostPlayerIdOrGuestPlayerId(pid, pid);
    }
}

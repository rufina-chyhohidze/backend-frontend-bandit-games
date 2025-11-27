package be.kdg.banditgames.platform.adapter.out.lobby;

import be.kdg.banditgames.common.shared.PlayerId;
import be.kdg.banditgames.common.shared.PlayerType;
import be.kdg.banditgames.platform.domain.Lobby;
import be.kdg.banditgames.platform.domain.vo.LobbyId;
import be.kdg.banditgames.platform.port.out.lobby.LoadLobbyPort;
import be.kdg.banditgames.platform.port.out.lobby.LobbyLookupPort;
import be.kdg.banditgames.platform.port.out.lobby.PersistLobbyPort;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class LobbyJpaAdapter implements LoadLobbyPort, PersistLobbyPort, LobbyLookupPort {

    private final LobbyJpaRepository lobbyJpaRepository;

    public LobbyJpaAdapter(LobbyJpaRepository lobbyJpaRepository) {
        this.lobbyJpaRepository = lobbyJpaRepository;
    }

    @Override
    public Optional<Lobby> loadLobbyById(LobbyId lobbyId) {
        return lobbyJpaRepository.findById(lobbyId.lobbyID())
                .map(LobbyJpaMapper::toDomain);
    }

    @Override
    public void saveLobby(Lobby lobby) {
        lobbyJpaRepository.save(LobbyJpaMapper.toEntity(lobby));
    }

    @Override
    public void removeLobby(LobbyId lobbyId) {
        lobbyJpaRepository.deleteById(lobbyId.lobbyID());
    }

    @Override
    public void addPlayerToLobby(LobbyId lobbyId, PlayerId playerId) {
        lobbyJpaRepository.findById(lobbyId.lobbyID()).ifPresent(lobbyEntity -> {
            if (lobbyEntity.getGuestPlayerId() == null) {
                lobbyEntity.setGuestPlayerId(playerId.playerId());
                lobbyEntity.setGuestType(PlayerType.HUMAN);
                lobbyJpaRepository.save(lobbyEntity);
            }
        });
    }

    @Override
    public boolean isPlayerInAnyLobby(PlayerId playerId) {
        return lobbyJpaRepository.existsByHostPlayerIdOrGuestPlayerId(playerId.playerId(), playerId.playerId());
    }
}

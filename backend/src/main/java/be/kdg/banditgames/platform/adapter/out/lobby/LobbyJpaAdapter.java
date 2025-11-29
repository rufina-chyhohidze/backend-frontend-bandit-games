package be.kdg.banditgames.platform.adapter.out.lobby;

import be.kdg.banditgames.common.shared.PlayerId;
import be.kdg.banditgames.common.shared.PlayerType;
import be.kdg.banditgames.platform.domain.Lobby;
import be.kdg.banditgames.platform.domain.exception.LobbyFullException;
import be.kdg.banditgames.platform.domain.exception.LobbyNotFoundException;
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
    public Lobby addPlayerToLobby(LobbyId lobbyId, PlayerId playerId) {
        LobbyJpaEntity lobbyEntity = lobbyJpaRepository.findById(lobbyId.lobbyID())
                .orElseThrow(() -> new LobbyNotFoundException("Lobby with ID " + lobbyId.lobbyID() + " not found."));

        if (lobbyEntity.getGuestPlayerId() == null) {
            lobbyEntity.setGuestPlayerId(playerId.playerId());
            lobbyEntity.setGuestType(PlayerType.HUMAN);

            LobbyJpaEntity updatedLobbyEntity = lobbyJpaRepository.save(lobbyEntity);

            return LobbyJpaMapper.toDomain(updatedLobbyEntity);
        } else {
            throw new LobbyFullException("Lobby with ID " + lobbyId.lobbyID() + " is already full.");
        }
    }

    @Override
    public boolean isPlayerInAnyLobby(PlayerId playerId) {
        return lobbyJpaRepository.existsByHostPlayerIdOrGuestPlayerId(playerId.playerId(), playerId.playerId());
    }

    @Override
    public Optional<Lobby> loadLobbyByPlayerId(PlayerId playerId) {
        return lobbyJpaRepository.findByHostPlayerIdOrGuestPlayerId(playerId.playerId(), playerId.playerId())
                .map(LobbyJpaMapper::toDomain);
    }
}

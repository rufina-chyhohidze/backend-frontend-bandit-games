package be.kdg.banditgames.platform.core;

import be.kdg.banditgames.common.shared.PlayerId;
import be.kdg.banditgames.common.shared.PlayerType;
import be.kdg.banditgames.platform.domain.Lobby;
import be.kdg.banditgames.platform.domain.exception.PlayerAlreadyInLobbyException;
import be.kdg.banditgames.platform.domain.vo.LobbyId;
import be.kdg.banditgames.platform.port.in.lobby.CreateLobbyCommand;
import be.kdg.banditgames.platform.port.in.lobby.LobbyCreationUseCase;
import be.kdg.banditgames.platform.port.in.lobby.ManagingLobbyUseCase;
import be.kdg.banditgames.platform.port.out.lobby.LoadLobbyPort;
import be.kdg.banditgames.platform.port.out.lobby.LobbyLookupPort;
import be.kdg.banditgames.platform.port.out.lobby.PersistLobbyPort;
import org.springframework.stereotype.Service;

@Service
public class LobbyUseCaseImpl implements LobbyCreationUseCase, ManagingLobbyUseCase {
    
    private final LoadLobbyPort loadLobbyPort;
    private final PersistLobbyPort persistLobbyPort;
    private final LobbyLookupPort lobbyLookupPort;
    
    public LobbyUseCaseImpl(LoadLobbyPort loadLobbyPort, PersistLobbyPort persistLobbyPort, LobbyLookupPort lobbyLookup) {
        this.loadLobbyPort = loadLobbyPort;
        this.persistLobbyPort = persistLobbyPort;
        this.lobbyLookupPort = lobbyLookup;
    }

    @Override
    public Lobby createLobby(CreateLobbyCommand createLobbyCommand) {
        PlayerId playerId = PlayerId.of(createLobbyCommand.playerId());

        if (lobbyLookupPort.isPlayerInAnyLobby(playerId)) {
            throw new PlayerAlreadyInLobbyException(playerId);
        }
        
        Lobby lobby = Lobby.createNew(playerId);
        persistLobbyPort.saveLobby(lobby);
        return lobby;
    }

    @Override
    public void closeLobby(LobbyId lobbyId) {
        persistLobbyPort.removeLobby(lobbyId);
    }

    @Override
    public Lobby addPlayerToLobby(PlayerId playerId, LobbyId lobbyId) {
        if (lobbyLookupPort.isPlayerInAnyLobby(playerId)) {
            throw new PlayerAlreadyInLobbyException(playerId);
        }

        Lobby lobby = loadLobbyPort.loadLobbyById(lobbyId)
                .orElseThrow();
        lobby.changeGuest(playerId, PlayerType.HUMAN);
        persistLobbyPort.saveLobby(lobby);
        return lobby;
    }

}

package be.kdg.banditgames.platform.port.in.lobby;

import be.kdg.banditgames.platform.domain.Lobby;
import be.kdg.banditgames.platform.domain.vo.LobbyId;

public interface LobbyCreationUseCase {
    
    Lobby createLobby(CreateLobbyCommand createLobbyCommand);
    void closeLobby(LobbyId lobbyId);
}

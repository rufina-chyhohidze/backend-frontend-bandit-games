package be.kdg.banditgames.platform.port.out.lobby;

import be.kdg.banditgames.common.shared.PlayerId;
import be.kdg.banditgames.platform.domain.Lobby;
import be.kdg.banditgames.platform.domain.vo.LobbyId;

public interface PersistLobbyPort {
    
    void saveLobby(Lobby lobby);
    void removeLobby(LobbyId lobbyId);
    Lobby addPlayerToLobby(LobbyId lobbyId, PlayerId playerId);
    
}

package be.kdg.banditgames.platform.port.in.lobby;

import be.kdg.banditgames.common.shared.PlayerId;
import be.kdg.banditgames.platform.domain.Lobby;
import be.kdg.banditgames.platform.domain.vo.LobbyId;

public interface ManagingLobbyUseCase {
    
    Lobby addPlayerToLobby(PlayerId playerId, LobbyId lobbyId);
    
}

package be.kdg.banditgames.platform.port.out.lobby;

import be.kdg.banditgames.common.shared.PlayerId;
import be.kdg.banditgames.platform.domain.Lobby;
import be.kdg.banditgames.platform.domain.vo.LobbyId;

import java.util.List;
import java.util.Optional;

public interface LoadLobbyPort {
    
    Optional<Lobby> loadLobbyById(LobbyId lobbyId);
    Optional<Lobby> loadLobbyByPlayerId(PlayerId playerId);
    List<Lobby> loadAll();
}

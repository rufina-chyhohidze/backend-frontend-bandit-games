package be.kdg.banditgames.platform.port.out.lobby;

import be.kdg.banditgames.platform.domain.Lobby;

import java.util.Optional;
import java.util.UUID;

public interface FindLobbyPort {
    
    Lobby findLobbyById(UUID lobbyId);
    Optional<Lobby> findLobbyByPlayerId(UUID playerId);
}

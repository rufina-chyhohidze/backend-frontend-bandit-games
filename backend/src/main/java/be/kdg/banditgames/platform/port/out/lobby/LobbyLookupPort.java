package be.kdg.banditgames.platform.port.out.lobby;

import be.kdg.banditgames.common.shared.PlayerId;

public interface LobbyLookupPort {
    boolean isPlayerInAnyLobby(PlayerId playerId);
}

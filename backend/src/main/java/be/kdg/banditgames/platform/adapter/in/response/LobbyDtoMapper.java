package be.kdg.banditgames.platform.adapter.in.response;

import be.kdg.banditgames.platform.domain.Lobby;

public class LobbyDtoMapper {

    public static LobbyDto toDto(Lobby lobby) {
        return new LobbyDto(
                lobby.getLobbyId().lobbyID(),
                lobby.getHostPlayer() != null ? lobby.getHostPlayer().playerId() : null,
                lobby.getHostType() != null ? lobby.getHostType().name() : null,
                lobby.getGuestPlayer() != null ? lobby.getGuestPlayer().playerId() : null,
                lobby.getGuestType() != null ? lobby.getGuestType().name() : null
        );
    }
}

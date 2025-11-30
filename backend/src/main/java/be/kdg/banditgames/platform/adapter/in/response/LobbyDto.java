package be.kdg.banditgames.platform.adapter.in.response;

import java.util.UUID;

public record LobbyDto(
        UUID lobbyId,
        UUID hostPlayerId,
        String hostType,
        UUID guestPlayerId,
        String guestType,
        UUID gameId
) {
}

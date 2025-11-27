package be.kdg.banditgames.platform.adapter.in.requests;

import java.util.UUID;

public record CreateLobbyRequest(
        UUID playerId
) {
}

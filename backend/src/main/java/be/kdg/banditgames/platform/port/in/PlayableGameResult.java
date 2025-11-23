package be.kdg.banditgames.platform.port.in;

import java.util.UUID;

public record PlayableGameResult(
        UUID gameId,
        String name,
        String description,
        String pictureUrl,
        String urlGameSession
) {
}

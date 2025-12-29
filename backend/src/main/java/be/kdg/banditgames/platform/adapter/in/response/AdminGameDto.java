package be.kdg.banditgames.platform.adapter.in.response;

import be.kdg.banditgames.platform.domain.Game;
import be.kdg.banditgames.platform.domain.GameStatus;

import java.util.UUID;

public record AdminGameDto(UUID gameId,
                           String name,
                           String description,
                           String rules,
                           String pictureUrl,
                           String urlGameSession,
                           GameStatus status

) {
    public static AdminGameDto fromDomain(Game game) {
        return new AdminGameDto(
                game.getGameId().gameId(),
                game.getName(),
                game.getDescription(),
                game.getRules(),
                game.getPictureUrl(),
                game.getUrlGameSession(),
                game.getStatus()
        );
    }
}

package be.kdg.banditgames.platform.port.in.player;

import be.kdg.banditgames.common.shared.GameId;
import be.kdg.banditgames.common.shared.PlayerId;

public record AddFavoriteGameCommand(PlayerId playerId, GameId gameId) {
}

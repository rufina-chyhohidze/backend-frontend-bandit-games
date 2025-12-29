package be.kdg.banditgames.platform.port.in.player;

import be.kdg.banditgames.common.shared.PlayerId;

import java.util.UUID;

public record ListFavoriteGamesCommand(PlayerId playerId) {
}

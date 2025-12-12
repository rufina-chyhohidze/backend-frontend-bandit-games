package be.kdg.banditgames.platform.port.in.achievement;

import be.kdg.banditgames.common.shared.GameId;
import be.kdg.banditgames.common.shared.PlayerId;

public record ListUnlockedAchievementsCommand(PlayerId requesterId, PlayerId playerId, GameId gameId) {
}

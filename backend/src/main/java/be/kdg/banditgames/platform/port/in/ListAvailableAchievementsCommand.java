package be.kdg.banditgames.platform.port.in;

import be.kdg.banditgames.gameplay.domain.vo.GameId;

public record ListAvailableAchievementsCommand(GameId gameId) {
}

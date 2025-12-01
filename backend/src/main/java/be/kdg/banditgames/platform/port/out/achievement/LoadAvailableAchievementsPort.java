package be.kdg.banditgames.platform.port.out.achievement;

import be.kdg.banditgames.common.shared.GameId;
import be.kdg.banditgames.platform.domain.Achievement;

import java.util.List;

public interface LoadAvailableAchievementsPort {
    List<Achievement> loadAvailableAchievements(GameId gameId);
}

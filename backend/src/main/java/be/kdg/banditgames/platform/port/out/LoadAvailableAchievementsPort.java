package be.kdg.banditgames.platform.port.out;

import be.kdg.banditgames.gameplay.domain.vo.GameId;
import be.kdg.banditgames.platform.domain.Achievement;

import java.util.List;

public interface LoadAvailableAchievementsPort {
    List<Achievement> loadAvailableAchievements(GameId gameId);
}

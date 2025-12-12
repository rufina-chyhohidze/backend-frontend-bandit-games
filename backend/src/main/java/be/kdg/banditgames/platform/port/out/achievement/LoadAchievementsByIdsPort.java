package be.kdg.banditgames.platform.port.out.achievement;

import be.kdg.banditgames.common.shared.GameId;
import be.kdg.banditgames.platform.domain.Achievement;

import java.util.List;
import java.util.UUID;

public interface LoadAchievementsByIdsPort {
    List<Achievement> loadByGameIdAndIds(GameId gameId, List<UUID>achievementsIds);
}

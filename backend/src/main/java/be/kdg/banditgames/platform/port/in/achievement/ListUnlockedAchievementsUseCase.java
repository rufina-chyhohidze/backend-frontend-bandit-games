package be.kdg.banditgames.platform.port.in.achievement;

import java.util.List;

public interface ListUnlockedAchievementsUseCase {
    List<UnlockedAchievementResult> listUnlockedAchievements(ListUnlockedAchievementsCommand command);

}

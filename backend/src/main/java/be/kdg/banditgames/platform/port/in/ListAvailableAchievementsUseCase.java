package be.kdg.banditgames.platform.port.in;

import java.util.List;

public interface ListAvailableAchievementsUseCase {
    List<AvailableAchievementResult> listAvailableAchievements(ListAvailableAchievementsCommand command);

}

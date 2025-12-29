package be.kdg.banditgames.platform.port.out.achievement;

import be.kdg.banditgames.platform.domain.Achievement;

public interface UpdateAchievementsPort {
    void save(Achievement achievement);
}

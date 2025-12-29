package be.kdg.banditgames.platform.adapter.out.achievement;

import be.kdg.banditgames.common.shared.AchievementId;
import be.kdg.banditgames.common.shared.GameId;
import be.kdg.banditgames.platform.domain.Achievement;

public class AchievementJpaMapper {
    private AchievementJpaMapper() {
    }

    public static Achievement toDomain(AchievementJpaEntity entity) {
        return Achievement.rehydrate(entity.toGameId(), entity.getName(), entity.getDescription(), entity.getUnlockHint());

    }

    public static AchievementJpaEntity toEntity(Achievement achievement) {
        AchievementJpaEntity entity = new AchievementJpaEntity();
        entity.setAchievementId(achievement.getAchievementId().achievementId());
        entity.setGameId(achievement.getGameId().gameId());
        entity.setName(achievement.getName());
        entity.setDescription(achievement.getDescription());
        entity.setUnlockHint(achievement.getUnlockHint());
        return entity;
    }
}

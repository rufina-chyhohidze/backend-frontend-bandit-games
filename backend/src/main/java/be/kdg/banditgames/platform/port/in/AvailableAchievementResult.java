package be.kdg.banditgames.platform.port.in;

import be.kdg.banditgames.platform.domain.Achievement;

public record AvailableAchievementResult(String achievementId,
                                         String name,
                                         String description,
                                         String unlockHint) {
    public static AvailableAchievementResult fromDomain(Achievement achievement) {
        // achievementId is a record, we want the underlying UUID
        String id = achievement.getAchievementId() != null
                ? achievement.getAchievementId().achievementId().toString()
                : null;

        return new AvailableAchievementResult(
                id,
                achievement.getName(),
                achievement.getDescription(),
                achievement.getUnlockHint()
        );
    }
}

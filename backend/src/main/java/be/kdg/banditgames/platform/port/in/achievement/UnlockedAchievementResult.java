package be.kdg.banditgames.platform.port.in.achievement;

import be.kdg.banditgames.platform.domain.Achievement;

public record UnlockedAchievementResult(
        String achievementId,
        String name,
        String description,
        String unlockHint
) {
    public static UnlockedAchievementResult fromDomain(Achievement a) {
        return new UnlockedAchievementResult(
                a.getAchievementId().achievementId().toString(),
                a.getName(),
                a.getDescription(),
                a.getUnlockHint()
        );
    }
}
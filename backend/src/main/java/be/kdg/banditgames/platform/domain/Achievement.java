package be.kdg.banditgames.platform.domain;

import be.kdg.banditgames.common.shared.AchievementId;
import be.kdg.banditgames.common.shared.GameId;

public class Achievement {
    private AchievementId achievementId;
    private final GameId gameId;
    private String name;
    private String description;
    private final String unlockHint;

    public Achievement(AchievementId achievementId, GameId gameId, String name, String description, String unlockHint) {
        this.achievementId = achievementId;
        this.gameId = gameId;
        this.name = name;
        this.description = description;
        this.unlockHint = unlockHint;
    }

    public AchievementId getAchievementId() {
        return achievementId;
    }

    public GameId getGameId() {
        return gameId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
    public String getUnlockHint() {
        return unlockHint;
    }

}

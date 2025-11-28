package be.kdg.banditgames.common.shared;

import java.util.UUID;

public record AchievementId(
        UUID achievementId
) {
    public static AchievementId of(UUID achievementId){
        return new AchievementId(achievementId);
    }
    
    public static AchievementId create(){
        return new AchievementId(UUID.randomUUID());
    }
}

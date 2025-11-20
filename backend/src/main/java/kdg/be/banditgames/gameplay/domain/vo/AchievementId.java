package kdg.be.banditgames.gameplay.domain.vo;

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

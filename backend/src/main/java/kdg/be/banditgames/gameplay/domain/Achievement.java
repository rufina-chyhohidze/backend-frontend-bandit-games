package kdg.be.banditgames.gameplay.domain;

import kdg.be.banditgames.gameplay.domain.vo.AchievementId;
import kdg.be.banditgames.gameplay.domain.vo.PlayerId;
import kdg.be.banditgames.gameplay.domain.vo.SessionId;

public class Achievement {
    PlayerId playerId;
    SessionId sessionId;
    AchievementId achievementId;
    String description;
    
    public Achievement(PlayerId playerId, SessionId sessionId, AchievementId achievementId, String description) {
        this.playerId = playerId;
        this.sessionId = sessionId;
        this.achievementId = achievementId;
        this.description = description;
    }
}

package be.kdg.banditgames.gameplay.domain;

import be.kdg.banditgames.gameplay.domain.vo.AchievementId;
import be.kdg.banditgames.gameplay.domain.vo.PlayerId;
import be.kdg.banditgames.gameplay.domain.vo.SessionId;

public class Achievement {
    private PlayerId playerId;
    private SessionId sessionId;
    private AchievementId achievementId;
    private String description;
    
    public Achievement(PlayerId playerId, SessionId sessionId, AchievementId achievementId, String description) {
        this.playerId = playerId;
        this.sessionId = sessionId;
        this.achievementId = achievementId;
        this.description = description;
    }
}

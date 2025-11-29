package be.kdg.banditgames.gameplay.domain;

import be.kdg.banditgames.common.shared.AchievementId;
import be.kdg.banditgames.common.shared.PlayerId;
import be.kdg.banditgames.common.shared.SessionId;

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

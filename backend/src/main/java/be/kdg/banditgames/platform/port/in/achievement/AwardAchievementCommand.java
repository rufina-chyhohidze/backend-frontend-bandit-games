package be.kdg.banditgames.platform.port.in.achievement;

import be.kdg.banditgames.common.shared.AchievementId;
import be.kdg.banditgames.common.shared.PlayerId;

public record AwardAchievementCommand(PlayerId playerId, AchievementId achievementId) {
}

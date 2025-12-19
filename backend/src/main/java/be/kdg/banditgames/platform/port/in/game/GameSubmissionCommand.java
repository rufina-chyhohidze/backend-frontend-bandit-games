package be.kdg.banditgames.platform.port.in.game;

import be.kdg.banditgames.common.events.generic.GenericAchievementDto;

import java.util.List;

public record GameSubmissionCommand(
        String name,
        String description,
        String rules,
        String pictureUrl,
        String urlGameSession,
        List<GenericAchievementDto> availableAchievements
) {
}

package be.kdg.banditgames.chessACL.requests;

import be.kdg.banditgames.common.events.generic.GenericAchievementDto;

import java.util.List;

public record RegisterGameRequest(
        String name,
        String description,
        String rules,
        String pictureUrl,
        String urlGameSession,
        List<GenericAchievementDto> availableAchievements
) {}
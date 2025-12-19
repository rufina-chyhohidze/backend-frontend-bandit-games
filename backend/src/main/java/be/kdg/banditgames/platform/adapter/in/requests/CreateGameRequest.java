package be.kdg.banditgames.platform.adapter.in.requests;

import be.kdg.banditgames.common.events.generic.GenericAchievementDto;

import java.util.List;

public record CreateGameRequest(
    String name,
    String description,
    String rules,
    String pictureUrl,
    String urlGameSession,
    List<GenericAchievementDto> availableAchievements
) {
}

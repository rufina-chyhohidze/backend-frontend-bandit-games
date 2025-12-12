package be.kdg.banditgames.platform.port.out.player;

import be.kdg.banditgames.common.shared.PlayerId;

import java.util.List;
import java.util.UUID;

public interface LoadPlayerAchievementIdsPort {
    List<UUID> loadAchievementIds(PlayerId playerId);
}

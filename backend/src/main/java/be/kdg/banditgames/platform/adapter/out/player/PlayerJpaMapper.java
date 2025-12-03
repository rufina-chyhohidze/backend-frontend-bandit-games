package be.kdg.banditgames.platform.adapter.out.player;

import be.kdg.banditgames.common.shared.AchievementId;
import be.kdg.banditgames.common.shared.GameId;
import be.kdg.banditgames.common.shared.PlayerId;
import be.kdg.banditgames.platform.domain.Player;

import java.util.UUID;

public class PlayerJpaMapper {
    public static PlayerJpaEntity toEntity(Player player) {
        return new PlayerJpaEntity(
                    player.getPlayerId().playerId(),
                    player.getUsername(),
                    player.getFavoriteGames().stream().map(GameId::gameId).toList(),
                    player.getAchievements().stream().map(AchievementId::achievementId).toList()
        );
    }

    public static Player toDomain(PlayerJpaEntity entity) {
        return Player.rehydrate(
                    PlayerId.of(entity.getId()),
                    entity.getUsername(),
                    entity.getFavoriteGames().stream().map(GameId::of).toList(),
                    entity.getAchievements().stream().map(AchievementId::of).toList()
        );
    }
}


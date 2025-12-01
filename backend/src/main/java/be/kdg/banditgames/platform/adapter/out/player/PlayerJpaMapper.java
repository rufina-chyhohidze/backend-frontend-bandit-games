package be.kdg.banditgames.platform.adapter.out.player;

import be.kdg.banditgames.common.shared.PlayerId;
import be.kdg.banditgames.platform.domain.Player;

import java.util.UUID;

public final class PlayerJpaMapper {
    private PlayerJpaMapper() {
    }

    public static Player toDomain(PlayerJpaEntity entity) {
        if (entity == null) return null;
        PlayerId playerId = PlayerId.of(entity.getId());
        return Player.rehydrate(playerId, entity.getUsername(), null, null);
    }

    public static PlayerJpaEntity toEntity(Player player) {
        if (player == null) return null;
        UUID id = player.getPlayerId().playerId();
        String username = player.getUsername();
        return new PlayerJpaEntity(id, username);
    }
}

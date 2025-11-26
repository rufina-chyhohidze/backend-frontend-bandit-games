package be.kdg.banditgames.platform.adapter.out.mapper;

import be.kdg.banditgames.gameplay.domain.vo.PlayerId;
import be.kdg.banditgames.platform.adapter.out.player.PlayerJpaEntity;
import be.kdg.banditgames.platform.domain.Player;

import java.util.UUID;

public final class PlayerJpaMapper {
    private PlayerJpaMapper() {
    }

    public static Player toDomain(PlayerJpaEntity entity) {
        if (entity == null) return null;
        PlayerId playerId = PlayerId.of(entity.getId());
        return new Player(playerId, entity.getUsername());
    }

    public static PlayerJpaEntity toEntity(Player player) {
        if (player == null) return null;
        UUID id = player.getPlayerId().playerId();
        String username = player.getUsername();
        return new PlayerJpaEntity(id, username);
    }
}

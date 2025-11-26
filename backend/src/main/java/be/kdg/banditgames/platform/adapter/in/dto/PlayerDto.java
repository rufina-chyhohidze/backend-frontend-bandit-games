package be.kdg.banditgames.platform.adapter.in.dto;

import be.kdg.banditgames.platform.domain.Player;
import be.kdg.banditgames.platform.domain.vo.AdminId;

import java.util.UUID;

public record PlayerDto (UUID id, String username) {
    public static PlayerDto fromDomain(Player player) {
        return new PlayerDto(
                player.getPlayerId().playerId(),
                player.getUsername()
        );
    }
}

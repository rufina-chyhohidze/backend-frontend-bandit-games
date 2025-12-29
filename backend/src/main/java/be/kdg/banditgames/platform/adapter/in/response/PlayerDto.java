package be.kdg.banditgames.platform.adapter.in.response;

import be.kdg.banditgames.platform.domain.Player;

import java.util.List;
import java.util.UUID;

public record PlayerDto (UUID id, String username, List<UUID> favoriteGameIds,List<String> achievements) {
    public static PlayerDto fromDomain(Player player) {
        return new PlayerDto(
                player.getPlayerId().playerId(),
                player.getUsername(),
                player.getFavoriteGames().stream()
                        .map(g -> g.gameId())
                        .toList(),
                player.getAchievements().stream()
                        .map(a -> a.achievementId().toString())
                        .toList()
        );
    }
}

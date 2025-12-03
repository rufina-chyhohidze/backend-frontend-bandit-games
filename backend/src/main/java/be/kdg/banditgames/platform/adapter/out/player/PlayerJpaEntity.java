package be.kdg.banditgames.platform.adapter.out.player;

import jakarta.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "players")
public class PlayerJpaEntity {

    @Id
    private UUID id;

    private String username;

    @ElementCollection
    @CollectionTable(name = "player_favorite_games", joinColumns = @JoinColumn(name = "player_id"))
    @Column(name = "game_id")
    private List<UUID> favoriteGames;

    @ElementCollection
    @CollectionTable(name = "player_achievements", joinColumns = @JoinColumn(name = "player_id"))
    @Column(name = "achievement_id")
    private List<UUID> achievements;

    protected PlayerJpaEntity() {
    }

    public PlayerJpaEntity(UUID id, String username, List<UUID> favoriteGames, List<UUID> achievements) {
        this.id = id;
        this.username = username;
        this.favoriteGames = favoriteGames;
        this.achievements = achievements;
    }

    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public List<UUID> getFavoriteGames() {
        return favoriteGames;
    }

    public List<UUID> getAchievements() {
        return achievements;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFavoriteGames(List<UUID> favoriteGames) {
        this.favoriteGames = favoriteGames;
    }

    public void setAchievements(List<UUID> achievements) {
        this.achievements = achievements;
    }
}

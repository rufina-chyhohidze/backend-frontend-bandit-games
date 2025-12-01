package be.kdg.banditgames.platform.adapter.out.player;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "players")
public class PlayerJpaEntity {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @ElementCollection
    @CollectionTable(
            name = "player_favorite_games",
            joinColumns = @JoinColumn(name = "player_id")
    )
    @Column(name = "game_id", nullable = false)
    private List<UUID> favoriteGameIds = new ArrayList<>();

    @ElementCollection
    @CollectionTable(
            name = "player_achievements",
            joinColumns = @JoinColumn(name = "player_id")
    )
    @Column(name = "achievement_id", nullable = false)
    private List<UUID> achievementIds = new ArrayList<>();

    protected PlayerJpaEntity() {
    }

    public PlayerJpaEntity(UUID id, String username) {
        this.id = id;
        this.username = username;
    }

    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public List<UUID> getFavoriteGameIds() {
        return favoriteGameIds;
    }

    public List<UUID> getAchievementIds() {
        return achievementIds;
    }

}

package be.kdg.banditgames.platform.adapter.out.achievement;

import be.kdg.banditgames.common.shared.AchievementId;
import be.kdg.banditgames.common.shared.GameId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "achievements")
public class AchievementJpaEntity {
    @Id
    @Column(name = "achievement_id", columnDefinition = "UUID")
    private UUID achievementId;

    @Column(name = "game_id", columnDefinition = "UUID", nullable = false)
    private UUID gameId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(name = "unlock_hint")
    private String unlockHint;

    public AchievementJpaEntity() {
    }

    public AchievementId toAchievementId() {
        return AchievementId.of(achievementId);
    }

    public GameId toGameId() {
        return GameId.of(gameId);
    }

    public UUID getAchievementId() {
        return achievementId;
    }

    public UUID getGameId() {
        return gameId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getUnlockHint() {
        return unlockHint;
    }

    public void setAchievementId(UUID achievementId) {
        this.achievementId = achievementId;
    }

    public void setGameId(UUID gameId) {
        this.gameId = gameId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUnlockHint(String unlockHint) {
        this.unlockHint = unlockHint;
    }
}

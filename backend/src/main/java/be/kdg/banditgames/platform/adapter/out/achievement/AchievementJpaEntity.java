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
    @Column(columnDefinition = "UUID")
    private UUID achievementId;

    @Column(columnDefinition = "UUID")
    private UUID gameId;

    private String name;
    private String description;
    private String unlockHint;

    public AchievementJpaEntity() {}

    public AchievementId toAchievementId() {
        return AchievementId.of(achievementId);
    }

    public GameId toGameId() {
        return GameId.of(gameId);
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getUnlockHint() { return unlockHint; }
}

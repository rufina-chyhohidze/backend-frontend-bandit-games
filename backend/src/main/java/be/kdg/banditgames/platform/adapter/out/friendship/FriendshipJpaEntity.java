package be.kdg.banditgames.platform.adapter.out.friendship;

import be.kdg.banditgames.common.shared.PlayerId;
import be.kdg.banditgames.platform.domain.FriendshipStatus;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "friendships",
        uniqueConstraints = @UniqueConstraint(columnNames = {"player_a_id", "player_b_id"}))
public class FriendshipJpaEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "player_a_id", nullable = false)
    private UUID playerAId;

    @Column(name = "player_b_id", nullable = false)
    private UUID playerBId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FriendshipStatus status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    protected FriendshipJpaEntity() {
    }

    public FriendshipJpaEntity(PlayerId playerA, PlayerId playerB, FriendshipStatus status) {
        this.playerAId = playerA.playerId();
        this.playerBId = playerB.playerId();
        this.status = status;
        this.createdAt = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public UUID getPlayerAId() {
        return playerAId;
    }

    public UUID getPlayerBId() {
        return playerBId;
    }

    public FriendshipStatus getStatus() {
        return status;
    }

    public void setStatus(FriendshipStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}

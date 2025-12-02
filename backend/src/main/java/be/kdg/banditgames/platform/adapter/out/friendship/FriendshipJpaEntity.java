package be.kdg.banditgames.platform.adapter.out.friendship;

import be.kdg.banditgames.platform.domain.FriendshipStatus;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "friendships", uniqueConstraints = {
        @UniqueConstraint(name = "ukgyt92a2snt4lnfcl5lg61ald8",
                columnNames = {"player_a_id", "player_b_id"})
})
public class FriendshipJpaEntity {

    @Id
    private UUID id;

    @Column(name = "player_a_id", nullable = false)
    private UUID playerAId;

    @Column(name = "player_b_id", nullable = false)
    private UUID playerBId;

    @Column(name = "initiator_id", nullable = false) 
    private UUID initiatorId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FriendshipStatus status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getPlayerAId() { return playerAId; }
    public void setPlayerAId(UUID playerAId) { this.playerAId = playerAId; }

    public UUID getPlayerBId() { return playerBId; }
    public void setPlayerBId(UUID playerBId) { this.playerBId = playerBId; }

    public UUID getInitiatorId() { return initiatorId; } // <-- NEW GETTER
    public void setInitiatorId(UUID initiatorId) { this.initiatorId = initiatorId; } // <-- NEW SETTER

    public FriendshipStatus getStatus() { return status; }
    public void setStatus(FriendshipStatus status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
package be.kdg.banditgames.platform.adapter.out.invitation;

import be.kdg.banditgames.platform.domain.InvitationStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "game_invitations")
public class GameInvitationJpaEntity {
    @Id
    private UUID id;

    @Column(nullable = false)
    private UUID fromPlayerId;

    @Column(nullable = false)
    private UUID toPlayerId;

    @Column(nullable = false)
    private UUID lobbyId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InvitationStatus status;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    protected GameInvitationJpaEntity() {}

    public GameInvitationJpaEntity(UUID id, UUID fromPlayerId, UUID toPlayerId, UUID lobbyId,
                                   InvitationStatus status, LocalDateTime createdAt) {
        this.id = id;
        this.fromPlayerId = fromPlayerId;
        this.toPlayerId = toPlayerId;
        this.lobbyId = lobbyId;
        this.status = status;
        this.createdAt = createdAt;
    }

    public UUID getId() { return id; }
    public UUID getFromPlayerId() { return fromPlayerId; }
    public UUID getToPlayerId() { return toPlayerId; }
    public UUID getLobbyId() { return lobbyId; }
    public InvitationStatus getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setStatus(InvitationStatus status) { this.status = status; }
}

package be.kdg.banditgames.platform.adapter.out.lobby;

import be.kdg.banditgames.common.shared.PlayerType;
import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "lobbies")
public class LobbyJpaEntity {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "host_player_id", nullable = false)
    private UUID hostPlayerId;

    @Enumerated(EnumType.STRING)
    @Column(name = "host_type", nullable = false)
    private PlayerType hostType;

    @Column(name = "guest_player_id")
    private UUID guestPlayerId;

    @Enumerated(EnumType.STRING)
    @Column(name = "guest_type")
    private PlayerType guestType;

    public LobbyJpaEntity() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getHostPlayerId() {
        return hostPlayerId;
    }

    public void setHostPlayerId(UUID hostPlayerId) {
        this.hostPlayerId = hostPlayerId;
    }

    public PlayerType getHostType() {
        return hostType;
    }

    public void setHostType(PlayerType hostType) {
        this.hostType = hostType;
    }

    public UUID getGuestPlayerId() {
        return guestPlayerId;
    }

    public void setGuestPlayerId(UUID guestPlayerId) {
        this.guestPlayerId = guestPlayerId;
    }

    public PlayerType getGuestType() {
        return guestType;
    }

    public void setGuestType(PlayerType guestType) {
        this.guestType = guestType;
    }
}

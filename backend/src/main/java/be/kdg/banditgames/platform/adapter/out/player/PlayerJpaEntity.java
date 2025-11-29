package be.kdg.banditgames.platform.adapter.out.player;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table (name = "players")
public class PlayerJpaEntity {
    @Id
    private UUID id;

    private String username;

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
}

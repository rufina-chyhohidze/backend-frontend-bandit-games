package be.kdg.banditgames.platform.adapter.out.admin;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "admins")
public class AdminJpaEntity {

    @Id
    private UUID id;

    protected AdminJpaEntity() {
    }

    public AdminJpaEntity(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }
}

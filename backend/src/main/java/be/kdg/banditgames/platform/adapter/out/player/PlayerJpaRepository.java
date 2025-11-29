package be.kdg.banditgames.platform.adapter.out.player;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PlayerJpaRepository extends JpaRepository<PlayerJpaEntity, UUID> {
}

package be.kdg.banditgames.platform.adapter.out.admin;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AdminJpaRepository extends JpaRepository<AdminJpaEntity, UUID> {
}

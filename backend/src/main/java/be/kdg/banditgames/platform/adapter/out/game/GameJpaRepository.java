package be.kdg.banditgames.platform.adapter.out.game;

import be.kdg.banditgames.platform.domain.GameStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface GameJpaRepository extends JpaRepository<GameJpaEntity, UUID> {
    List<GameJpaEntity> findByStatus(GameStatus status);
    List<GameJpaEntity> findByIdIn(List<UUID> ids);

}

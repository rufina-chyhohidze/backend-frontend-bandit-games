package be.kdg.banditgames.platform.adapter.out.achievement;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AchievementJpaRepository extends JpaRepository<AchievementJpaEntity, UUID> {
    List<AchievementJpaEntity> findByGameId(UUID gameId);

}

package be.kdg.banditgames.platform.adapter.out.ml;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MoveRecommendedProjectionJpaRepository  extends JpaRepository<MoveRecommendedProjectionJpaEntity, UUID> {
}

package be.kdg.banditgames.platform.adapter.out.ml;

import be.kdg.banditgames.common.shared.SessionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface MoveRecommendedProjectionJpaRepository extends JpaRepository<MoveRecommendedProjectionJpaEntity, UUID> {

    @Transactional(readOnly = true)
    List<MoveRecommendedProjectionJpaEntity> findBySessionId(SessionId sessionId);
}

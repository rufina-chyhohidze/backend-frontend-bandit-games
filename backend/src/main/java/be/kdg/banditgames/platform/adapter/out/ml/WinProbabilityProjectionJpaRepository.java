package be.kdg.banditgames.platform.adapter.out.ml;

import be.kdg.banditgames.common.shared.SessionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WinProbabilityProjectionJpaRepository extends JpaRepository<WinProbabilityProjectionJpaEntity, UUID> {

    @Transactional(readOnly = true)
    Optional<WinProbabilityProjectionJpaEntity> findFirstBySessionIdOrderByMoveNumberDesc(SessionId sessionId);

    @Transactional(readOnly = true)
    Optional<WinProbabilityProjectionJpaEntity> findBySessionIdAndMoveNumber(SessionId sessionId, int moveNumber);

    @Transactional(readOnly = true)
    List<WinProbabilityProjectionJpaEntity> findBySessionId(SessionId sessionId);
}

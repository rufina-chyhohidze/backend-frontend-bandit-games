package be.kdg.banditgames.platform.adapter.out.ml;
import be.kdg.banditgames.platform.domain.MoveRecommendedProjection;
import be.kdg.banditgames.platform.port.out.ml.PersistMoveRecommendedProjectionPort;
import org.springframework.stereotype.Repository;

@Repository
public class MoveRecommendedProjectionAdapter implements PersistMoveRecommendedProjectionPort {
    private final MoveRecommendedProjectionJpaRepository repository;

    public MoveRecommendedProjectionAdapter(MoveRecommendedProjectionJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public void saveMoveProbabilityProjection(MoveRecommendedProjection moveProjection) {
        MoveRecommendedProjectionJpaEntity entity = new MoveRecommendedProjectionJpaEntity(
                moveProjection.sessionId(),
                moveProjection.moveNumber(),
                moveProjection.aiType(),
                moveProjection.gameState(),
                moveProjection.legalMoves(),
                moveProjection.recommendedMove(),
                moveProjection.confidence());
        repository.save(entity);
    }
}

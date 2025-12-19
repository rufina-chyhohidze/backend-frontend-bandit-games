package be.kdg.banditgames.platform.port.out.ml;

import be.kdg.banditgames.platform.domain.MoveRecommendedProjection;

public interface PersistMoveRecommendedProjectionPort {
    void saveMoveProbabilityProjection(MoveRecommendedProjection moveProjection);
}

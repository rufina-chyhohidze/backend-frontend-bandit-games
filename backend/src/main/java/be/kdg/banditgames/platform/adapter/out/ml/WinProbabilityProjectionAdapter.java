package be.kdg.banditgames.platform.adapter.out.ml;

import be.kdg.banditgames.common.shared.PlayerType;
import be.kdg.banditgames.common.shared.SessionId;
import be.kdg.banditgames.platform.domain.WinProbabilityProjection;
import be.kdg.banditgames.platform.port.out.ml.PersistWinProbabilityProjectionPort;
import org.springframework.stereotype.Repository;
import be.kdg.banditgames.platform.adapter.out.ml.WinProbabilityProjectionJpaEntity;

import java.util.List;

@Repository
public class WinProbabilityProjectionAdapter implements PersistWinProbabilityProjectionPort {
    private final WinProbabilityProjectionJpaRepository repository;

    public WinProbabilityProjectionAdapter(WinProbabilityProjectionJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public void saveWinProbabilityProjection(WinProbabilityProjection projection) {
        WinProbabilityProjectionJpaEntity entity = new WinProbabilityProjectionJpaEntity(
                projection.sessionId(),
                projection.moveNumber(),
                projection.aiType(),
                projection.gameState(),
                projection.legalMoves(),
                projection.player1WinProbability(),
                projection.player2WinProbability(),
                projection.activePlayerWinProbability(),
                projection.distribution());
        repository.save(entity);
    }
}
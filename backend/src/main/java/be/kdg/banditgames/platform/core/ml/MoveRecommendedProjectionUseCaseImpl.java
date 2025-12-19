package be.kdg.banditgames.platform.core.ml;

import be.kdg.banditgames.common.shared.PlayerType;
import be.kdg.banditgames.common.shared.SessionId;
import be.kdg.banditgames.platform.domain.MoveRecommendedProjection;
import be.kdg.banditgames.platform.port.in.ml.AddMoveRecommendedPort;
import be.kdg.banditgames.platform.port.in.ml.CreateMoveRecommendedCommand;
import be.kdg.banditgames.platform.port.out.ml.PersistMoveRecommendedProjectionPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class MoveRecommendedProjectionUseCaseImpl implements AddMoveRecommendedPort {
    private final PersistMoveRecommendedProjectionPort persistMoveRecommendedProjectionPort;

    public MoveRecommendedProjectionUseCaseImpl(PersistMoveRecommendedProjectionPort persistMoveRecommendedProjectionPort) {
        this.persistMoveRecommendedProjectionPort = persistMoveRecommendedProjectionPort;
    }

    @Override
    public void addMoveProbability(CreateMoveRecommendedCommand command) {
        MoveRecommendedProjection projection = new MoveRecommendedProjection(
                SessionId.of(command.sessionId()),
                command.moveNumber(),
                PlayerType.valueOf(command.aiType()),
                command.gameState(),
                command.legalMoves(),
                command.recommendedMove(),
                command.confidence());
        persistMoveRecommendedProjectionPort.saveMoveProbabilityProjection(projection);

    }
}
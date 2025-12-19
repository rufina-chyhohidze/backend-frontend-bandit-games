package be.kdg.banditgames.platform.core.ml;

import be.kdg.banditgames.common.shared.PlayerType;
import be.kdg.banditgames.common.shared.SessionId;
import be.kdg.banditgames.platform.domain.WinProbabilityProjection;
import be.kdg.banditgames.platform.port.in.ml.AddWinProbabilityPort;
import be.kdg.banditgames.platform.port.in.ml.CreateWinProbabilityCommand;
import be.kdg.banditgames.platform.port.out.ml.PersistWinProbabilityProjectionPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class WinProbabilityProjectionUseCaseImpl implements AddWinProbabilityPort {

    private final PersistWinProbabilityProjectionPort persistWinProbabilityProjectionPort;

    public WinProbabilityProjectionUseCaseImpl(PersistWinProbabilityProjectionPort persistWinProbabilityProjectionPort) {
        this.persistWinProbabilityProjectionPort = persistWinProbabilityProjectionPort;
    }


    @Override
    public void addWinProbability(CreateWinProbabilityCommand command) {
        WinProbabilityProjection winProbability = new WinProbabilityProjection(SessionId.of(command.sessionId()), command.moveNumber(), PlayerType.valueOf(command.aiType()), command.gameState(), command.legalMoves(), command.player1WinProbability(), command.player2WinProbability(), command.activePlayerWinProbability(), command.distribution());
        persistWinProbabilityProjectionPort.saveWinProbabilityProjection(winProbability);
    }

}

package be.kdg.banditgames.gameplay.core;

import be.kdg.banditgames.common.events.ml.MLWinProbabilityEvent;
import be.kdg.banditgames.gameplay.adapter.out.mlWinProbability.MLWinProbabilityPublisher;
import be.kdg.banditgames.gameplay.domain.WinProbability;
import be.kdg.banditgames.gameplay.port.in.winProbability.GetWinProbabilityCommand;
import be.kdg.banditgames.gameplay.port.out.mlWinProbability.MLWinProbabilityService;
import be.kdg.banditgames.gameplay.port.out.mlWinProbability.MLWinProbabilityUseCase;
import org.springframework.stereotype.Service;

@Service
public class WinProbabilityUseCaseImpl implements MLWinProbabilityUseCase {

    private final MLWinProbabilityService winProbabilityService;
    private final MLWinProbabilityPublisher winProbabilityPublisher;
    
    public WinProbabilityUseCaseImpl(
            MLWinProbabilityService winProbabilityService,
            MLWinProbabilityPublisher winProbabilityPublisher
    ) {
        this.winProbabilityService = winProbabilityService;
        this.winProbabilityPublisher = winProbabilityPublisher;
    }

    @Override
    public WinProbability handleWinProbability(GetWinProbabilityCommand getWinProbabilityCommand) {
        WinProbability winProbability =
                winProbabilityService.getWinProbability(getWinProbabilityCommand);
        
        winProbabilityPublisher.publish(new MLWinProbabilityEvent(
                getWinProbabilityCommand.sessionId(),
                getWinProbabilityCommand.moveNumber(),
                getWinProbabilityCommand.aiType(),
                getWinProbabilityCommand.gameState(),
                getWinProbabilityCommand.legalMoves(),
                winProbability.player1WinProbability(),
                winProbability.player2WinProbability(),
                winProbability.activePlayerWinProbability(),
                winProbability.distribution()
        ));
        return winProbability;
    }
}

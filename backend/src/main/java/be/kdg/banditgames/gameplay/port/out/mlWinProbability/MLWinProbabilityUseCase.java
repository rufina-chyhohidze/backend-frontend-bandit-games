package be.kdg.banditgames.gameplay.port.out.mlWinProbability;

import be.kdg.banditgames.gameplay.domain.WinProbability;
import be.kdg.banditgames.gameplay.port.in.winProbability.GetWinProbabilityCommand;

public interface MLWinProbabilityUseCase {
    WinProbability handleWinProbability(GetWinProbabilityCommand getWinProbabilityCommand);
}

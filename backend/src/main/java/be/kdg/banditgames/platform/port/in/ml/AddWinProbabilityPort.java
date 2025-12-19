package be.kdg.banditgames.platform.port.in.ml;

import java.util.UUID;

public interface AddWinProbabilityPort {
    void addWinProbability(CreateWinProbabilityCommand command);

}

package be.kdg.banditgames.platform.port.out.ml;

import be.kdg.banditgames.platform.domain.WinProbabilityProjection;

public interface PersistWinProbabilityProjectionPort {
    void saveWinProbabilityProjection(WinProbabilityProjection projection);
}

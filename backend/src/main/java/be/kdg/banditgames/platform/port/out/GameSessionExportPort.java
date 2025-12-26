package be.kdg.banditgames.platform.port.out;

import be.kdg.banditgames.platform.port.out.dto.GameSessionExportData;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GameSessionExportPort {
    List<GameSessionExportData> findAllSessionsOrderedByStartTime();
    Optional<GameSessionExportData> findSessionById(UUID sessionId);
}

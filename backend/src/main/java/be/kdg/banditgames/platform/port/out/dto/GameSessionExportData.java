package be.kdg.banditgames.platform.port.out.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record GameSessionExportData(
        UUID sessionId,
        UUID gameId,
        String player1Type,
        String player2Type,
        String gameSessionState,
        LocalDateTime startTime,
        LocalDateTime endTime,
        String gameResult,
        List<GameStateExportData> gameStates
) {}

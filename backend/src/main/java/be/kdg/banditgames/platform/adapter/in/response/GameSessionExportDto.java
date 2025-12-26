package be.kdg.banditgames.platform.adapter.in.response;

import java.time.LocalDateTime;

public record GameSessionExportDto(
        String sessionId,
        String gameId,
        String player1Type,
        String player2Type,
        String gameResult,
        LocalDateTime startTime,
        LocalDateTime endTime,
        int moveCount
) {}

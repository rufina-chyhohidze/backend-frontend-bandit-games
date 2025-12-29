package be.kdg.banditgames.platform.adapter.in.response;

import java.time.LocalDateTime;
import java.util.List;

public record GameReplayExportDto(
        String sessionId,
        String gameId,
        String player1Type,
        String player2Type,
        String gameResult,
        LocalDateTime startTime,
        LocalDateTime endTime,
        List<MoveReplayExportDto> moves
) {}

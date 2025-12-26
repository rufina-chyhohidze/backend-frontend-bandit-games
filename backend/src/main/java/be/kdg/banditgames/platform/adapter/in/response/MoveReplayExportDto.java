package be.kdg.banditgames.platform.adapter.in.response;

import java.time.LocalDateTime;

public record MoveReplayExportDto(
        int moveNumber,
        String playerType,
        String playerSide,
        String boardState,
        String legalMoves,
        Integer actualMove,
        Integer aiHardRecommendedMove,
        Double aiHardConfidence,
        Double aiHardWinProbability,
        Integer aiMlRecommendedMove,
        Double aiMlConfidence,
        Double aiMlWinProbability,
        LocalDateTime timestamp
) {}

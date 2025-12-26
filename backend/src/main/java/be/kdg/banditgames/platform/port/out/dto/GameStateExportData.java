package be.kdg.banditgames.platform.port.out.dto;

import java.time.LocalDateTime;

public record GameStateExportData(
        LocalDateTime timestamp,
        String playerType,
        String playerSide,
        int moveNumber,
        String board,
        String legalMoves,
        AiMetadataExportData aiMetadata
) {}

package be.kdg.banditgames.platform.port.out.dto;

public record AiMetadataExportData(
        String recommendedMove,
        Double confidenceScore,
        String bestMove,
        Double heuristicScore,
        Integer visitCount,
        Integer searchDepth
) {}

package be.kdg.banditgames.platform.adapter.in.response;

public record WinProbabilityDto(
        String sessionId,
        int moveNumber,
        double player1WinProbability,
        double player2WinProbability,
        double activePlayerWinProbability
) {}

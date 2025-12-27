package be.kdg.banditgames.gameplay.domain.events;

import java.util.UUID;

public record AiMoveCalculatedInternalEvent(
        UUID sessionId,
        int moveNumber,
        String aiType,
        String gameState,
        String legalMoves
) {}

package be.kdg.banditgames.gameplay.adapter.in.request;

import java.util.UUID;

public record AiRequest(
        UUID sessionId,
        int moveNumber,
        String gameState,
        String legalMoves
) {
}

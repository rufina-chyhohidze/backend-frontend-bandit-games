package be.kdg.banditgames.gameplay.port.in.AiTurn;

import java.util.UUID;

public record ProcessAiTurnCommand(
        UUID sessionId,
        String serializedBoard,
        String legalMoves
) {}
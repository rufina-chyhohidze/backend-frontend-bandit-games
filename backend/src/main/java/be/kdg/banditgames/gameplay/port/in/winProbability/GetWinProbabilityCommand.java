package be.kdg.banditgames.gameplay.port.in.winProbability;

import java.util.UUID;

public record GetWinProbabilityCommand(
        UUID sessionId,
        int moveNumber,
        String AiType,
        String gameState,
        String legalMoves
){
}

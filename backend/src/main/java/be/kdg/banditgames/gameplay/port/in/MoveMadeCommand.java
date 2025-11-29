package be.kdg.banditgames.gameplay.port.in;

import be.kdg.banditgames.common.shared.PlayerSide;
import be.kdg.banditgames.common.shared.PlayerType;

import java.time.LocalDateTime;
import java.util.UUID;

public record MoveMadeCommand(
        UUID eventId,
        LocalDateTime occurredAt,
        UUID sessionId,
        PlayerType playerType,
        PlayerSide playerSide,
        int moveNumber,
        String serializedBoard,
        String serializedLegalMoves
) {
}

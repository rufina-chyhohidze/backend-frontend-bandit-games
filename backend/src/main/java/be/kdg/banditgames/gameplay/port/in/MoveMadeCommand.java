package be.kdg.banditgames.gameplay.port.in;

import be.kdg.banditgames.common.events.gameplay.PlayerSide;
import be.kdg.banditgames.common.events.gameplay.PlayerType;

import java.time.LocalDateTime;
import java.util.UUID;

public record MoveMadeCommand(
        UUID eventId,
        LocalDateTime occurredAt,
        UUID gameId,
        UUID sessionId,
        PlayerType playerType,
        PlayerSide playerSide,
        int moveNumber,
        String serializedBoard,
        String serializedLegalMoves
) {
}

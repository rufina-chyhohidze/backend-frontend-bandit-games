package be.kdg.banditgames.common.events;

import be.kdg.banditgames.common.shared.PlayerSide;
import be.kdg.banditgames.common.shared.PlayerType;

import java.time.LocalDateTime;
import java.util.UUID;

public record MoveMadeEvent(
        UUID eventId,
        LocalDateTime occurredAt,
        UUID gameId,
        UUID sessionId,
        PlayerType playerType,
        PlayerSide playerSide,
        int moveNumber,
        String serializedBoard,
        String serializedLegalMoves
) implements DomainEvent {
    public MoveMadeEvent(UUID gameId, UUID sessionId,PlayerType playerType, PlayerSide playerSide, int moveNumber, String serializedBoard, String serializedLegalMoves) {
        this(
                UUID.randomUUID(),
                LocalDateTime.now(),
                gameId,
                sessionId,
                playerType,
                playerSide,
                moveNumber,
                serializedBoard,
                serializedLegalMoves
        );
    }

    @Override
    public LocalDateTime eventPit() {
        return occurredAt;
    }
}

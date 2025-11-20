package be.kdg.banditgames.common.events.gameplay;

import be.kdg.banditgames.common.events.DomainEvent;

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

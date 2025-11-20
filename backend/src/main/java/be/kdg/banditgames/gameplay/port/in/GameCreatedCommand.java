package be.kdg.banditgames.gameplay.port.in;

import be.kdg.banditgames.common.events.gameplay.PlayerType;

import java.time.LocalDateTime;
import java.util.UUID;

public record GameCreatedCommand(
        UUID eventId,
        LocalDateTime occurredAt,
        UUID gameId,
        UUID sessionId,
        PlayerType player1,
        PlayerType player2
) {
}
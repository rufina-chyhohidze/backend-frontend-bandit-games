package be.kdg.banditgames.gameplay.port.in.gameSession;

import be.kdg.banditgames.common.shared.PlayerType;

import java.time.LocalDateTime;
import java.util.UUID;

public record GameSessionCreatedCommand(
        UUID eventId,
        LocalDateTime occurredAt,
        UUID sessionId,
        UUID gameId,
        PlayerType player1,
        PlayerType player2
) {
}
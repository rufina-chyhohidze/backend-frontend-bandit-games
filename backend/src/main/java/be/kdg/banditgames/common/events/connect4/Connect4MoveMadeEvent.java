package be.kdg.banditgames.common.events.connect4;
import be.kdg.banditgames.common.events.DomainEvent;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import be.kdg.banditgames.common.shared.PlayerSide;
import be.kdg.banditgames.common.shared.PlayerType;

import java.time.LocalDateTime;
import java.util.UUID;

public record Connect4MoveMadeEvent(
        UUID eventId,
        LocalDateTime occurredAt,
        UUID sessionId,
        PlayerType playerType,
        PlayerSide playerSide,
        int moveNumber,
        String serializedBoard,
        String serializedLegalMoves
) implements DomainEvent {

    @JsonCreator
    public Connect4MoveMadeEvent(
            @JsonProperty("sessionId") UUID sessionId,
            @JsonProperty("playerType") PlayerType playerType,
            @JsonProperty("playerSide") PlayerSide playerSide,
            @JsonProperty("moveNumber") int moveNumber,
            @JsonProperty("serializedBoard") String serializedBoard,
            @JsonProperty("serializedLegalMoves") String serializedLegalMoves
    ) {
        this(UUID.randomUUID(),
                LocalDateTime.now(),
                sessionId,
                playerType,
                playerSide,
                moveNumber,
                serializedBoard,
                serializedLegalMoves);
    }


    @Override
    public LocalDateTime eventPit() {
        return occurredAt;
    }
}

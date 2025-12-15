package be.kdg.banditgames.common.events.generic;

import be.kdg.banditgames.common.events.DomainEvent;
import be.kdg.banditgames.common.shared.PlayerSide;
import be.kdg.banditgames.common.shared.PlayerType;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.modulith.events.Externalized;

import java.time.LocalDateTime;
import java.util.UUID;

public record GenericMoveMadeEvent(
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
    public GenericMoveMadeEvent(
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

package be.kdg.banditgames.common.events.connect4;

import be.kdg.banditgames.common.events.DomainEvent;
import be.kdg.banditgames.common.shared.PlayerType;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.modulith.events.Externalized;

import java.time.LocalDateTime;
import java.util.UUID;

@Externalized("connect4.events::#{'connect4.achievement.' + #this.playerId()}")
public record AchievementEvent(
        UUID eventId,
        LocalDateTime occurredAt,
        String playerId,
        String achievementId

) implements DomainEvent {

    @JsonCreator
    public AchievementEvent(
            @JsonProperty("playerId") String playerId,
            @JsonProperty("achievementId") String achievementId
    ) {
        this(UUID.randomUUID(), LocalDateTime.now(), playerId, achievementId);
    }


    @Override
    public LocalDateTime eventPit() {
        return occurredAt;
    }
}

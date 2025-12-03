package be.kdg.banditgames.platform.adapter.in.response;

import be.kdg.banditgames.platform.domain.FriendshipStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record FriendshipDto(
        UUID playerA,
        UUID playerB,
        FriendshipStatus status,
        LocalDateTime createdAt,
        UUID initiatorId

) {
}

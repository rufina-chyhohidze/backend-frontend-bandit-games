package be.kdg.banditgames.platform.adapter.in.requests.friendship;

import java.util.UUID;

public record FriendRequestDto(
        UUID fromPlayerId,
        UUID toPlayerId
) {}
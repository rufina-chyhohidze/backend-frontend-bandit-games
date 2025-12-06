package be.kdg.banditgames.platform.adapter.in.requests.friendship;

import java.util.UUID;

public record RemoveFriendDto(
        UUID playerAId,
        UUID playerBId
) {}
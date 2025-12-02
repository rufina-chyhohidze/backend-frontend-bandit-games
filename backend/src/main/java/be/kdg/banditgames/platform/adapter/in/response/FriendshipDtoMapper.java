package be.kdg.banditgames.platform.adapter.in.response;

import be.kdg.banditgames.platform.domain.Friendship;

public class FriendshipDtoMapper {
    public static FriendshipDto toDto(Friendship friendship) {
        return new FriendshipDto(
                friendship.getPlayerA().playerId(),
                friendship.getPlayerB().playerId(),
                friendship.getStatus(),
                friendship.getCreatedAt(),
                friendship.getInitiator().playerId()
        );
    }
}

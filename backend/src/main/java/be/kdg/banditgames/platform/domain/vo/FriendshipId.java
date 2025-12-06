package be.kdg.banditgames.platform.domain.vo;

import java.util.UUID;

public record FriendshipId(
        UUID friendshipId
) {
    public static FriendshipId of(UUID id) {
        return new FriendshipId(id);
    }
    
    public static FriendshipId create() {
        return new FriendshipId(UUID.randomUUID());
    }
    
}

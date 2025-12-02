package be.kdg.banditgames.platform.adapter.out.friendship;

import be.kdg.banditgames.common.shared.PlayerId;
import be.kdg.banditgames.platform.domain.Friendship;
import be.kdg.banditgames.platform.domain.FriendshipStatus;

public class FriendshipJpaMapper {

    public static FriendshipJpaEntity toJpaEntity(Friendship domain) {
        FriendshipJpaEntity entity = new FriendshipJpaEntity();
        entity.setId(domain.getId().friendshipId());
        entity.setPlayerAId(domain.getPlayerA().playerId());
        entity.setPlayerBId(domain.getPlayerB().playerId());
        entity.setInitiatorId(domain.getInitiator().playerId());
        entity.setStatus(domain.getStatus());
        entity.setCreatedAt(domain.getCreatedAt());
        return entity;
    }

    public static Friendship toDomain(FriendshipJpaEntity entity) {
        return Friendship.rehydrate(
                entity.getId(),
                PlayerId.of(entity.getPlayerAId()),
                PlayerId.of(entity.getPlayerBId()),
                switch (entity.getStatus()) {
                    case PENDING -> FriendshipStatus.PENDING;
                    case ACCEPTED -> FriendshipStatus.ACCEPTED;
                    case REJECTED -> FriendshipStatus.REJECTED;
                    case BLOCKED -> FriendshipStatus.BLOCKED;
                },
                entity.getCreatedAt(),
                PlayerId.of(entity.getInitiatorId()) // MAPPING OUT
        );
    }
}
package be.kdg.banditgames.platform.adapter.out.friendship;

import be.kdg.banditgames.common.shared.PlayerId;
import be.kdg.banditgames.platform.domain.Friendship;
import be.kdg.banditgames.platform.domain.FriendshipStatus;

public class FriendshipJpaMapper {

    public static FriendshipJpaEntity toJpaEntity(Friendship domain) {
        return new FriendshipJpaEntity(
                domain.getPlayerA(),
                domain.getPlayerB(),
                FriendshipStatus.valueOf(domain.getStatus().name())
        );
    }

    public static Friendship toDomain(FriendshipJpaEntity entity) {
        return Friendship.rehydrate(
                PlayerId.of(entity.getPlayerAId()),
                PlayerId.of(entity.getPlayerBId()),
                switch (entity.getStatus()) {
                    case PENDING -> FriendshipStatus.PENDING;
                    case ACCEPTED -> FriendshipStatus.ACCEPTED;
                    case REJECTED -> FriendshipStatus.REJECTED;
                    case BLOCKED -> FriendshipStatus.BLOCKED;
                },
                entity.getCreatedAt()
        );
    }
}

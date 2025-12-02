package be.kdg.banditgames.platform.adapter.out.friendship;

import be.kdg.banditgames.common.shared.PlayerId;
import be.kdg.banditgames.platform.domain.Friendship;
import be.kdg.banditgames.platform.port.out.friendship.LoadFriendshipPort;
import be.kdg.banditgames.platform.port.out.friendship.PersistFriendshipPort;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FriendshipJpaAdapter implements PersistFriendshipPort, LoadFriendshipPort {
    
    private final FriendshipJpaRepository friendshipJpaRepository;
    
    public FriendshipJpaAdapter(FriendshipJpaRepository friendshipJpaRepository) {
        this.friendshipJpaRepository = friendshipJpaRepository;
    }

    @Override
    public List<Friendship> loadFriendshipsForPlayer(PlayerId playerId) {
        return friendshipJpaRepository.findAllByPlayerAIdOrPlayerBId(playerId.playerId(), playerId.playerId())
                .stream()
                .map(FriendshipJpaMapper::toDomain)
                .toList();
    }

    @Override
    public void saveFriendship(Friendship friendship) {
        FriendshipJpaEntity friendshipJpaEntity = FriendshipJpaMapper.toJpaEntity(friendship);
        friendshipJpaRepository.save(friendshipJpaEntity);
    }

    @Override
    public void removeFriendship(Friendship friendship) {
        FriendshipJpaEntity friendshipJpaEntity = FriendshipJpaMapper.toJpaEntity(friendship);
        friendshipJpaRepository.delete(friendshipJpaEntity);
    }
}

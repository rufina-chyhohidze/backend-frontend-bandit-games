package be.kdg.banditgames.platform.adapter.out.friendship;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FriendshipJpaRepository extends JpaRepository<FriendshipJpaEntity, UUID> {
    List<FriendshipJpaEntity> findAllByPlayerAIdOrPlayerBId(UUID playerAId, UUID playerBId);
}

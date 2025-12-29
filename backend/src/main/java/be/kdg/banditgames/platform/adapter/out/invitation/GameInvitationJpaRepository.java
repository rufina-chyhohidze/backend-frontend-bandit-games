package be.kdg.banditgames.platform.adapter.out.invitation;

import be.kdg.banditgames.platform.adapter.out.game.GameJpaEntity;
import be.kdg.banditgames.platform.domain.InvitationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GameInvitationJpaRepository extends JpaRepository<GameInvitationJpaEntity, UUID> {
    List<GameInvitationJpaEntity> findAllByToPlayerIdAndStatus(UUID toPlayerId, InvitationStatus status);

    @Query("""
        select gi
        from GameInvitationJpaEntity gi
        where gi.status = 'PENDING'
          and (
            (gi.fromPlayerId = :a and gi.toPlayerId = :b)
            or
            (gi.fromPlayerId = :b and gi.toPlayerId = :a)
          )
        """)
    Optional<GameInvitationJpaEntity> findPendingBetween(UUID a, UUID b);
}

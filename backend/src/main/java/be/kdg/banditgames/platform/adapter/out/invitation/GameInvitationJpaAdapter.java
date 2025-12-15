package be.kdg.banditgames.platform.adapter.out.invitation;

import be.kdg.banditgames.common.shared.PlayerId;
import be.kdg.banditgames.platform.domain.GameInvitation;
import be.kdg.banditgames.platform.domain.InvitationStatus;
import be.kdg.banditgames.platform.domain.vo.InvitationId;
import be.kdg.banditgames.platform.port.out.invitation.LoadGameInvitationPort;
import be.kdg.banditgames.platform.port.out.invitation.PersistGameInvitationPort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class GameInvitationJpaAdapter implements LoadGameInvitationPort, PersistGameInvitationPort {
    private final GameInvitationJpaRepository repo;

    public GameInvitationJpaAdapter(GameInvitationJpaRepository repo) {
        this.repo = repo;
    }

    @Override
    public Optional<GameInvitation> loadById(InvitationId id) {
        return repo.findById(id.id()).map(GameInvitationJpaMapper::toDomain);
    }

    @Override
    public List<GameInvitation> loadPendingForPlayer(PlayerId toPlayer) {
        return repo.findAllByToPlayerIdAndStatus(toPlayer.playerId(), InvitationStatus.PENDING)
                .stream()
                .map(GameInvitationJpaMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<GameInvitation> findPendingBetween(PlayerId a, PlayerId b) {
        return repo.findPendingBetween(a.playerId(), b.playerId())
                .map(GameInvitationJpaMapper::toDomain);
    }

    @Override
    public void save(GameInvitation invitation) {
        repo.save(GameInvitationJpaMapper.toEntity(invitation));
    }
}


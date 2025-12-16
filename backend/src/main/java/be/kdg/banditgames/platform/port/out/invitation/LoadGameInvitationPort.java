package be.kdg.banditgames.platform.port.out.invitation;

import be.kdg.banditgames.common.shared.PlayerId;
import be.kdg.banditgames.platform.domain.GameInvitation;
import be.kdg.banditgames.platform.domain.vo.InvitationId;

import java.util.List;
import java.util.Optional;

public interface LoadGameInvitationPort {
    Optional<GameInvitation> loadById(InvitationId id);

    List<GameInvitation> loadPendingForPlayer(PlayerId toPlayer);

    Optional<GameInvitation> findPendingBetween(PlayerId a, PlayerId b);
}

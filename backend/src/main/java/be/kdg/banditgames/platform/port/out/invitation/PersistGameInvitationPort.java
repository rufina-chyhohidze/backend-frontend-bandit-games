package be.kdg.banditgames.platform.port.out.invitation;

import be.kdg.banditgames.platform.domain.GameInvitation;
import be.kdg.banditgames.platform.domain.vo.InvitationId;

public interface PersistGameInvitationPort {
    void save(GameInvitation invitation);
    void delete (InvitationId id);
}

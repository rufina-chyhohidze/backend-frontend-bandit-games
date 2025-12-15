package be.kdg.banditgames.platform.port.out.invitation;

import be.kdg.banditgames.platform.domain.GameInvitation;

public interface PersistGameInvitationPort {
    void save(GameInvitation invitation);
}

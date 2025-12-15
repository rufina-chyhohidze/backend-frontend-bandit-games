package be.kdg.banditgames.platform.adapter.out.invitation;

import be.kdg.banditgames.common.shared.PlayerId;
import be.kdg.banditgames.platform.domain.GameInvitation;
import be.kdg.banditgames.platform.domain.vo.InvitationId;
import be.kdg.banditgames.platform.domain.vo.LobbyId;

public class GameInvitationJpaMapper {
    public static GameInvitation toDomain(GameInvitationJpaEntity e) {
        return GameInvitation.rehydrate(
                InvitationId.of(e.getId()),
                new PlayerId(e.getFromPlayerId()),
                new PlayerId(e.getToPlayerId()),
                LobbyId.of(e.getLobbyId()),
                e.getStatus(),
                e.getCreatedAt()
        );
    }

    public static GameInvitationJpaEntity toEntity(GameInvitation d) {
        return new GameInvitationJpaEntity(
                d.getId().id(),
                d.getFromPlayer().playerId(),
                d.getToPlayer().playerId(),
                d.getLobbyId().lobbyID(),
                d.getStatus(),
                d.getCreatedAt()
        );
    }
}

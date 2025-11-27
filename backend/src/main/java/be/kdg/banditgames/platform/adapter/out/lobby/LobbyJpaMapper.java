package be.kdg.banditgames.platform.adapter.out.lobby;

import be.kdg.banditgames.common.shared.PlayerId;
import be.kdg.banditgames.platform.domain.Lobby;
import be.kdg.banditgames.platform.domain.vo.LobbyId;

public class LobbyJpaMapper {

    public static Lobby toDomain(LobbyJpaEntity entity) {
        return Lobby.rehydrate(
                new LobbyId(entity.getId()),
                new PlayerId(entity.getHostPlayerId()),
                entity.getHostType(),
                entity.getGuestPlayerId() != null ? new PlayerId(entity.getGuestPlayerId()) : null,
                entity.getGuestType()
        );
    }

    public static LobbyJpaEntity toEntity(Lobby lobby) {
        LobbyJpaEntity entity = new LobbyJpaEntity();
        entity.setId(lobby.getLobbyId().lobbyID());
        entity.setHostPlayerId(lobby.getHostPlayer().playerId());
        entity.setHostType(lobby.getHostType());
        entity.setGuestPlayerId(lobby.getGuestPlayer() != null ? lobby.getGuestPlayer().playerId() : null);
        entity.setGuestType(lobby.getGuestType());
        return entity;
    }
}

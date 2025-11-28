package be.kdg.banditgames.platform.adapter.out.lobby;

import be.kdg.banditgames.common.shared.GameId;
import be.kdg.banditgames.common.shared.PlayerId;
import be.kdg.banditgames.platform.domain.Lobby;
import be.kdg.banditgames.platform.domain.vo.LobbyId;

public class LobbyJpaMapper {

    public static Lobby toDomain(LobbyJpaEntity entity) {
        return Lobby.rehydrate(
                LobbyId.of(entity.getId()),
                PlayerId.of(entity.getHostPlayerId()),
                entity.getHostType(),
                entity.getGuestPlayerId() != null ? new PlayerId(entity.getGuestPlayerId()) : null,
                entity.getGuestType(),
                entity.getStatus(),
                GameId.of(entity.getGameId())
        );
    }

    public static LobbyJpaEntity toEntity(Lobby lobby) {
        LobbyJpaEntity entity = new LobbyJpaEntity();
        entity.setId(lobby.getLobbyId().lobbyID());
        entity.setHostPlayerId(lobby.getHostPlayer().playerId());
        entity.setHostType(lobby.getHostType());
        entity.setGuestPlayerId(lobby.getGuestPlayer() != null ? lobby.getGuestPlayer().playerId() : null);
        entity.setGuestType(lobby.getGuestType());
        entity.setStatus(lobby.getLobbyStatus());
        entity.setGameId(lobby.getGameId() != null ? lobby.getGameId().gameId() : null);
        return entity;
    }
}

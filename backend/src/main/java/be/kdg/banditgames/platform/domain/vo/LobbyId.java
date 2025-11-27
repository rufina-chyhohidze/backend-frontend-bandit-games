package be.kdg.banditgames.platform.domain.vo;

import java.util.UUID;

public record LobbyId(
        UUID lobbyID
) {
    public static LobbyId create(){
        return new LobbyId(UUID.randomUUID());
    }
    
    public static LobbyId of(UUID lobbyId){
        return new LobbyId(lobbyId);
    }
    
}

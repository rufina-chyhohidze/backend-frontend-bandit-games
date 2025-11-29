package be.kdg.banditgames.common.shared;

import java.util.UUID;

public record PlayerId(
        UUID playerId
) {
    public static PlayerId of(UUID playerId){
        return new PlayerId(playerId);
    }
    
    public static PlayerId create(){
        return new PlayerId(UUID.randomUUID());
    }
}

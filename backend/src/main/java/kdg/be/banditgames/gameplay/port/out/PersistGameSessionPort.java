package kdg.be.banditgames.gameplay.port.out;

import kdg.be.banditgames.gameplay.domain.GameSession;
import kdg.be.banditgames.gameplay.domain.GameState;
import kdg.be.banditgames.gameplay.domain.vo.SessionId;

public interface PersistGameSessionPort {
    
    GameSession save(GameSession gameSession);
    void addGameState(SessionId sessionId, GameState gameState);
}

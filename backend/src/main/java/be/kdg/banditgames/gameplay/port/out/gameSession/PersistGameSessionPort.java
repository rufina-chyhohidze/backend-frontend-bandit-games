package be.kdg.banditgames.gameplay.port.out.gameSession;

import be.kdg.banditgames.gameplay.domain.GameSession;
import be.kdg.banditgames.gameplay.domain.GameState;
import be.kdg.banditgames.gameplay.domain.vo.SessionId;

public interface PersistGameSessionPort {
    
    void save(GameSession gameSession);
    void addGameState(SessionId sessionId, GameState gameState);
}

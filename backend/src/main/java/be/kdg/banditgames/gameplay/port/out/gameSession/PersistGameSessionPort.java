package be.kdg.banditgames.gameplay.port.out.gameSession;

import be.kdg.banditgames.gameplay.domain.GameResult;
import be.kdg.banditgames.gameplay.domain.GameSession;
import be.kdg.banditgames.gameplay.domain.GameState;
import be.kdg.banditgames.common.shared.SessionId;

import java.time.LocalDateTime;

public interface PersistGameSessionPort {
    
    void save(GameSession gameSession);
    void appendMove(SessionId sessionId, GameState gameState);
    void markCompleted(SessionId id, GameResult result, LocalDateTime endTime);
}

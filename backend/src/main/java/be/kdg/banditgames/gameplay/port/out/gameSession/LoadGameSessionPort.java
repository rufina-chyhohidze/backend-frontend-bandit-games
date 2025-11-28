package be.kdg.banditgames.gameplay.port.out.gameSession;

import be.kdg.banditgames.gameplay.domain.GameSession;
import be.kdg.banditgames.gameplay.domain.vo.SessionId;

import java.util.Optional;

public interface LoadGameSessionPort {
    Optional<GameSession> loadGameSessionById(SessionId sessionId);
    
}

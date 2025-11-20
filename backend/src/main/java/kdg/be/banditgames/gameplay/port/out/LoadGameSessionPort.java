package kdg.be.banditgames.gameplay.port.out;

import kdg.be.banditgames.gameplay.domain.GameSession;
import kdg.be.banditgames.gameplay.domain.vo.SessionId;

import java.util.Optional;

public interface LoadGameSessionPort {
    
    Optional<GameSession> loadGameSessionById(SessionId sessionId);
    
}

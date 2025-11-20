package be.kdg.banditgames.gameplay.core;

import be.kdg.banditgames.gameplay.domain.GameState;
import be.kdg.banditgames.gameplay.domain.RecommendedMove;
import be.kdg.banditgames.gameplay.port.out.gameSession.PersistGameSessionPort;
import be.kdg.banditgames.gameplay.port.out.recommendedMove.RecommendedMovePort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class GameplayService{
    private final PersistGameSessionPort persistGameSessionPort;
    private final RecommendedMovePort recommendedMovePort;


    public GameplayService(PersistGameSessionPort persistGameSessionPort, RecommendedMovePort recommendedMovePort) {
        this.persistGameSessionPort = persistGameSessionPort;
        this.recommendedMovePort = recommendedMovePort;
    }
    
    public RecommendedMove handleNewMove(GameState gameState) {
        persistGameSessionPort.addGameState(gameState.getSessionId(), gameState);
        return recommendedMovePort.getRecommendedMove(gameState);
    }
}

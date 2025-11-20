//package kdg.be.banditgames.gameplay.core;
//
//import kdg.be.banditgames.gameplay.domain.GameState;
//import kdg.be.banditgames.gameplay.port.in.PublishGameStateUseCase;
//import kdg.be.banditgames.gameplay.port.out.gameSession.GameStateEventPublisherPort;
//import kdg.be.banditgames.gameplay.port.out.gameSession.LoadGameSessionPort;
//import kdg.be.banditgames.gameplay.port.out.gameSession.PersistGameSessionPort;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//
//@Service
//@Transactional
//public class PublishGameStateUseCaseImpl implements PublishGameStateUseCase {
//    
//    private final GameStateEventPublisherPort gameStateEventPublisherPort;
//    private final LoadGameSessionPort loadGameSessionPort;
//    private final PersistGameSessionPort persistGameSessionPort;
//    
//    public PublishGameStateUseCaseImpl(GameStateEventPublisherPort gameStateEventPublisherPort,
//                                       LoadGameSessionPort loadGameSessionPort,
//                                       PersistGameSessionPort persistGameSessionPort) {   
//        this.gameStateEventPublisherPort = gameStateEventPublisherPort;
//        this.loadGameSessionPort = loadGameSessionPort;
//        this.persistGameSessionPort = persistGameSessionPort;
//    }
//
//    @Override
//    public GameState publishGameState(GameState gameState) {
//        persistGameSessionPort.addGameState(gameState.getSessionId(), gameState);
//        gameStateEventPublisherPort.publishEvent(gameState);
//        return gameState;
//    }
//}

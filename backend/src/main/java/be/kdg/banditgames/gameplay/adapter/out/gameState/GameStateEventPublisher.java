//package kdg.be.banditgames.gameplay.adapter.out.gameState;
//
//import kdg.be.banditgames.gameplay.domain.GameState;
//import kdg.be.banditgames.gameplay.port.out.gameSession.GameStateEventPublisherPort;
//import org.springframework.context.ApplicationEventPublisher;
//import org.springframework.stereotype.Component;
//
//@Component
//public class GameStateEventPublisher implements GameStateEventPublisherPort {
//
//    private final ApplicationEventPublisher applicationEventPublisher;
//
//    public GameStateEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
//        this.applicationEventPublisher = applicationEventPublisher;
//    }
//
//    @Override
//    public void publishEvent(GameState gameState) {
//        gameState.getDomainEvents().forEach(applicationEventPublisher::publishEvent);
//        gameState.clearDomainEvents();
//    }
//}

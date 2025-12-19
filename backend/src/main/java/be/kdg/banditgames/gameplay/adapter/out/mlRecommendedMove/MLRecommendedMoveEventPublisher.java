package be.kdg.banditgames.gameplay.adapter.out.mlRecommendedMove;

import be.kdg.banditgames.gameplay.domain.RecommendedMove;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class MLRecommendedMoveEventPublisher {

    private final ApplicationEventPublisher publisher;

    public MLRecommendedMoveEventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    public void publish(RecommendedMove recommendedMove) {
        publisher.publishEvent(recommendedMove);
    }
}

package be.kdg.banditgames.gameplay.adapter.out.mlWinProbability;

import be.kdg.banditgames.common.events.ml.MLWinProbabilityEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class MLWinProbabilityPublisher {

    private final ApplicationEventPublisher publisher;

    public MLWinProbabilityPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    public void publish(MLWinProbabilityEvent winProbability) {
        publisher.publishEvent(winProbability);
    }
}

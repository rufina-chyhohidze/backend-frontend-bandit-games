package be.kdg.banditgames.gameplay.adapter.out.mlWinProbability;

import be.kdg.banditgames.gameplay.domain.WinProbability;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class MLWinProbabilityPublisher {

    private final ApplicationEventPublisher publisher;

    public MLWinProbabilityPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    public void publish(WinProbability winProbability) {
        publisher.publishEvent(winProbability);
    }
}

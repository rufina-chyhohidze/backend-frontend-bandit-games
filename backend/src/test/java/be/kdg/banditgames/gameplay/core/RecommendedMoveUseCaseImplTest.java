package be.kdg.banditgames.gameplay.core;

import be.kdg.banditgames.gameplay.adapter.out.mlRecommendedMove.MLRecommendedMoveEventPublisher;
import be.kdg.banditgames.gameplay.domain.RecommendedMove;
import be.kdg.banditgames.gameplay.port.in.MLRecommendedMove.GetRecommendedMoveCommand;
import be.kdg.banditgames.gameplay.port.out.MLRecommendedMove.MLRecommendedMoveService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class RecommendedMoveUseCaseImplTest {

    @Test
    void handleMove_delegatesToService_andPublishesEvent() {
        MLRecommendedMoveService service = mock(MLRecommendedMoveService.class);
        MLRecommendedMoveEventPublisher publisher = mock(MLRecommendedMoveEventPublisher.class);

        RecommendedMoveUseCaseImpl useCase = new RecommendedMoveUseCaseImpl(service, publisher);

        GetRecommendedMoveCommand cmd = new GetRecommendedMoveCommand(
                java.util.UUID.randomUUID(), 2, "AI", "state", "moves"
        );

        when(service.getRecommendedMove(cmd)).thenReturn(new RecommendedMove("a1a2", 0.5));

        RecommendedMove result = useCase.handleMove(cmd);

        assertEquals("a1a2", result.move());
        verify(service, times(1)).getRecommendedMove(cmd);
        verify(publisher, times(1)).publish(any());
    }
}


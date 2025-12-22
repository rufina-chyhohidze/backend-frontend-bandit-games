package be.kdg.banditgames.gameplay.core;

import be.kdg.banditgames.gameplay.adapter.out.mlWinProbability.MLWinProbabilityPublisher;
import be.kdg.banditgames.gameplay.domain.WinProbability;
import be.kdg.banditgames.gameplay.port.in.winProbability.GetWinProbabilityCommand;
import be.kdg.banditgames.gameplay.port.out.mlWinProbability.MLWinProbabilityService;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class WinProbabilityUseCaseImplTest {

    @Test
    void handleWinProbability_delegatesToService_andPublishesEvent() {
        MLWinProbabilityService service = mock(MLWinProbabilityService.class);
        MLWinProbabilityPublisher publisher = mock(MLWinProbabilityPublisher.class);

        WinProbabilityUseCaseImpl useCase = new WinProbabilityUseCaseImpl(service, publisher);

        GetWinProbabilityCommand cmd = new GetWinProbabilityCommand(
                java.util.UUID.randomUUID(), 3, "AI", "state", "moves"
        );

        WinProbability wp = new WinProbability(0.6, 0.4, 0.6, List.of(0.6, 0.4));
        when(service.getWinProbability(cmd)).thenReturn(wp);

        WinProbability result = useCase.handleWinProbability(cmd);

        assertEquals(0.6, result.player1WinProbability());
        verify(service, times(1)).getWinProbability(cmd);
        verify(publisher, times(1)).publish(any());
    }
}


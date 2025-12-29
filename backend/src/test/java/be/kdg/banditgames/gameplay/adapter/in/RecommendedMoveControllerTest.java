package be.kdg.banditgames.gameplay.adapter.in;

import be.kdg.banditgames.gameplay.adapter.in.request.MLRecommendedMoveRequest;
import be.kdg.banditgames.gameplay.adapter.in.response.MLRecommendedMoveDto;
import be.kdg.banditgames.gameplay.domain.RecommendedMove;
import be.kdg.banditgames.gameplay.domain.WinProbability;
import be.kdg.banditgames.gameplay.port.in.MLRecommendedMove.GetRecommendedMoveCommand;
import be.kdg.banditgames.gameplay.port.in.winProbability.GetWinProbabilityCommand;
import be.kdg.banditgames.gameplay.port.out.MLRecommendedMove.MLRecommendedMoveUseCase;
import be.kdg.banditgames.gameplay.port.out.mlWinProbability.MLWinProbabilityUseCase;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RecommendedMoveControllerTest {

    @Test
    void recommendedMove_mapsAndReturnsDto() {
        // Arrange
        MLRecommendedMoveUseCase recommendedMoveUseCase = mock(MLRecommendedMoveUseCase.class);
        MLWinProbabilityUseCase winProbabilityUseCase = mock(MLWinProbabilityUseCase.class);

        MachineLearningController controller = new MachineLearningController(recommendedMoveUseCase, winProbabilityUseCase);

        UUID sessionId = UUID.randomUUID();
        MLRecommendedMoveRequest request = new MLRecommendedMoveRequest(sessionId, 1, "CHESS_AI", "state", "moves");

        RecommendedMove recommendedMove = new RecommendedMove("e2e4", 0.92);
        when(recommendedMoveUseCase.handleMove(new GetRecommendedMoveCommand(
                request.sessionId(), request.moveNumber(), request.AiType(), request.gameState(), request.legalMoves()
        ))).thenReturn(recommendedMove);

        WinProbability winProbability = new WinProbability(0.7, 0.3, 0.7, List.of(0.7, 0.3));
        when(winProbabilityUseCase.handleWinProbability(new GetWinProbabilityCommand(
                request.sessionId(), request.moveNumber(), request.AiType(), request.gameState(), request.legalMoves()
        ))).thenReturn(winProbability);

        // Act
        var response = controller.recommendedMove(request);

        // Assert
        MLRecommendedMoveDto body = response.getBody();
        assertEquals("e2e4", body.recommendedMove());
        assertEquals(0.7, body.winProbability(), 1e-9);
    }
}


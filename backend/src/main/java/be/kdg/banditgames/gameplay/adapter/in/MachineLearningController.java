package be.kdg.banditgames.gameplay.adapter.in;


import be.kdg.banditgames.gameplay.adapter.in.request.AiRequest;
import be.kdg.banditgames.gameplay.adapter.in.request.MLRecommendedMoveRequest;
import be.kdg.banditgames.gameplay.adapter.in.response.MLRecommendedMoveDto;
import be.kdg.banditgames.gameplay.domain.RecommendedMove;
import be.kdg.banditgames.gameplay.domain.WinProbability;
import be.kdg.banditgames.gameplay.port.in.MLRecommendedMove.GetRecommendedMoveCommand;
import be.kdg.banditgames.gameplay.port.in.winProbability.GetWinProbabilityCommand;
import be.kdg.banditgames.gameplay.port.out.MLRecommendedMove.MLRecommendedMoveUseCase;
import be.kdg.banditgames.gameplay.port.out.mlWinProbability.MLWinProbabilityUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/gameplay/ml-move")
public class MachineLearningController {
    
    private final MLRecommendedMoveUseCase recommendedMoveUseCase;
    private final MLWinProbabilityUseCase winProbabilityUseCase;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    public MachineLearningController(MLRecommendedMoveUseCase recommendedMoveUseCase,
                                     MLWinProbabilityUseCase winProbabilityUseCase
    ) {
        this.recommendedMoveUseCase = recommendedMoveUseCase;
        this.winProbabilityUseCase = winProbabilityUseCase;
    }
    
    @PostMapping
    public ResponseEntity<MLRecommendedMoveDto> recommendedMove(@RequestBody AiRequest request) {

        logger.info("recommendedMove has been called");

        RecommendedMove recommendedMove = recommendedMoveUseCase.handleMove(new GetRecommendedMoveCommand(
                request.sessionId(),
                request.moveNumber(),
                request.AiType(),
                request.gameState(),
                request.legalMoves()
        ));
        
        WinProbability winProbability = winProbabilityUseCase.handleWinProbability(new GetWinProbabilityCommand(
                request.sessionId(),
                request.moveNumber(),
                request.AiType(),
                request.gameState(),
                request.legalMoves()
        ));
        
        // just map to DTO
        MLRecommendedMoveDto recommendedMoveDto = new MLRecommendedMoveDto(
                recommendedMove.move(),
                winProbability.player1WinProbability()
        );
        return ResponseEntity.ok(recommendedMoveDto);
    }
    
}

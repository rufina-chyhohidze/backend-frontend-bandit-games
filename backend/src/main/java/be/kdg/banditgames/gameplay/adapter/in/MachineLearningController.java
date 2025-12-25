package be.kdg.banditgames.gameplay.adapter.in;


import be.kdg.banditgames.gameplay.adapter.in.request.MLRecommendedMoveRequest;
import be.kdg.banditgames.gameplay.adapter.in.response.MLRecommendedMoveDto;
import be.kdg.banditgames.gameplay.domain.RecommendedMove;
import be.kdg.banditgames.gameplay.domain.WinProbability;
import be.kdg.banditgames.gameplay.port.in.MLRecommendedMove.GetRecommendedMoveCommand;
import be.kdg.banditgames.gameplay.port.in.winProbability.GetWinProbabilityCommand;
import be.kdg.banditgames.gameplay.port.out.MLRecommendedMove.MLRecommendedMoveUseCase;
import be.kdg.banditgames.gameplay.port.out.mlWinProbability.MLWinProbabilityUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/gameplay/recommended-move")
public class RecommendedMoveController {
    
    private final MLRecommendedMoveUseCase recommendedMoveUseCase;
    private final MLWinProbabilityUseCase winProbabilityUseCase;
    
    public RecommendedMoveController(MLRecommendedMoveUseCase recommendedMoveUseCase,
                                     MLWinProbabilityUseCase winProbabilityUseCase
    ) {
        this.recommendedMoveUseCase = recommendedMoveUseCase;
        this.winProbabilityUseCase = winProbabilityUseCase;
    }
    
    @GetMapping
    public ResponseEntity<MLRecommendedMoveDto> recommendedMove(@RequestBody MLRecommendedMoveRequest mlRecommendedMoveRequest) {

        RecommendedMove recommendedMove = recommendedMoveUseCase.handleMove(new GetRecommendedMoveCommand(
                mlRecommendedMoveRequest.sessionId(),
                mlRecommendedMoveRequest.moveNumber(),
                mlRecommendedMoveRequest.aiType(),
                mlRecommendedMoveRequest.gameState(),
                mlRecommendedMoveRequest.legalMoves()
        ));
        
        WinProbability winProbability = winProbabilityUseCase.handleWinProbability(new GetWinProbabilityCommand(
                mlRecommendedMoveRequest.sessionId(),
                mlRecommendedMoveRequest.moveNumber(),
                mlRecommendedMoveRequest.aiType(),
                mlRecommendedMoveRequest.gameState(),
                mlRecommendedMoveRequest.legalMoves()
        ));
        
        // just map to DTO
        MLRecommendedMoveDto recommendedMoveDto = new MLRecommendedMoveDto(
                recommendedMove.move(),
                winProbability.player1WinProbability()
        );
        return ResponseEntity.ok(recommendedMoveDto);
    }
    
}

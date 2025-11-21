package be.kdg.banditgames.gameplay.adapter.in;


import be.kdg.banditgames.gameplay.adapter.in.request.MLRecommendedMoveRequest;
import be.kdg.banditgames.gameplay.adapter.in.response.MLRecommendedMoveDto;
import be.kdg.banditgames.gameplay.domain.RecommendedMove;
import be.kdg.banditgames.gameplay.port.in.MLRecommendedMoveCommand.GetRecommendedMoveCommand;
import be.kdg.banditgames.gameplay.port.out.MLRecommendedMove.MLRecommendedMoveUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/gameplay/recommended-move")
public class RecommendedMoveController {
    
    private final MLRecommendedMoveUseCase recommendedMoveUseCase;
    
    public RecommendedMoveController(MLRecommendedMoveUseCase recommendedMoveUseCase) {
        this.recommendedMoveUseCase = recommendedMoveUseCase;
    }
    
    @GetMapping
    public ResponseEntity<MLRecommendedMoveDto> recommendedMove(@RequestBody MLRecommendedMoveRequest mlRecommendedMoveRequest) {

        RecommendedMove recommendedMove = recommendedMoveUseCase.handleMove(new GetRecommendedMoveCommand(
                mlRecommendedMoveRequest.gameState(),
                mlRecommendedMoveRequest.legalMoves()
        ));
        
        MLRecommendedMoveDto recommendedMoveDto = new MLRecommendedMoveDto(
                recommendedMove.move(),
                recommendedMove.confidenceScore()
        );
        return ResponseEntity.ok(recommendedMoveDto);
    }
    
}

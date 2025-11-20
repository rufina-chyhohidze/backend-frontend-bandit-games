package be.kdg.banditgames.gameplay.adapter.in;


import be.kdg.banditgames.gameplay.adapter.in.request.AiRequestDto;
import be.kdg.banditgames.gameplay.adapter.in.response.RecommendedMoveDto;
import be.kdg.banditgames.gameplay.core.RecommendedMoveUseCaseImpl;
import be.kdg.banditgames.gameplay.adapter.out.recommendedMove.AiRequest;
import be.kdg.banditgames.gameplay.domain.RecommendedMove;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/gameplay/best-move")
public class BestMoveController {
    
    private final RecommendedMoveUseCaseImpl recommendedMoveUseCase;

    public BestMoveController(RecommendedMoveUseCaseImpl recommendedMoveUseCase) {
        this.recommendedMoveUseCase = recommendedMoveUseCase;
    }

    @GetMapping
    public ResponseEntity<RecommendedMoveDto> getBestMove(@RequestBody AiRequestDto aiRequestDto) {
        RecommendedMove recommendedMove = recommendedMoveUseCase.handleNewMove(
                new AiRequest(
                        aiRequestDto.gameState(), aiRequestDto.legalMoves()
                )
        );
        
        RecommendedMoveDto recommendedMoveDto = new RecommendedMoveDto(recommendedMove.gameState());
        return ResponseEntity.ok(recommendedMoveDto);
        }
}

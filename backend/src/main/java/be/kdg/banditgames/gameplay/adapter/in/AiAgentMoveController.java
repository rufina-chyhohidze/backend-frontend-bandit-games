package be.kdg.banditgames.gameplay.adapter.in;


import be.kdg.banditgames.gameplay.adapter.in.request.AiRequestDto;
import be.kdg.banditgames.gameplay.adapter.in.response.AiAgentMoveDto;
import be.kdg.banditgames.gameplay.core.AiAgentMoveUseCaseImpl;
import be.kdg.banditgames.gameplay.adapter.out.recommendedMove.AiRequest;
import be.kdg.banditgames.gameplay.domain.RecommendedMove;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/gameplay/ai-move")
public class AiAgentMoveController {
    
    private final AiAgentMoveUseCaseImpl recommendedMoveUseCase;

    public AiAgentMoveController(AiAgentMoveUseCaseImpl recommendedMoveUseCase) {
        this.recommendedMoveUseCase = recommendedMoveUseCase;
    }

    @GetMapping
    public ResponseEntity<AiAgentMoveDto> getBestMove(@RequestBody AiRequestDto aiRequestDto) {
        RecommendedMove recommendedMove = recommendedMoveUseCase.handleNewMove(
                new AiRequest(
                        aiRequestDto.gameState(), aiRequestDto.legalMoves()
                )
        );
        
        AiAgentMoveDto aiAgentMoveDto = new AiAgentMoveDto(recommendedMove.gameState());
        return ResponseEntity.ok(aiAgentMoveDto);
        }
}

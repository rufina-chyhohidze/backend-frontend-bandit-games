package be.kdg.banditgames.gameplay.adapter.in;


import be.kdg.banditgames.gameplay.adapter.in.request.AiRequest;
import be.kdg.banditgames.gameplay.adapter.in.response.AiAgentMoveDto;
import be.kdg.banditgames.gameplay.core.AiAgentMoveUseCaseImpl;
import be.kdg.banditgames.gameplay.domain.AiMove;
import be.kdg.banditgames.gameplay.port.in.AiRequestCommand;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/gameplay/ai-move")
public class AiAgentMoveController {
    
    private final AiAgentMoveUseCaseImpl aiAgentMoveUseCase;

    public AiAgentMoveController(AiAgentMoveUseCaseImpl aiAgentMoveUseCase) {
        this.aiAgentMoveUseCase = aiAgentMoveUseCase;
    }

    @GetMapping
    public ResponseEntity<AiAgentMoveDto> getBestMove(@RequestBody AiRequest aiRequest) {
        AiMove aiMove = aiAgentMoveUseCase.handleMove(
                new AiRequestCommand(
                        aiRequest.gameState(), aiRequest.legalMoves()
                )
        );
        
        AiAgentMoveDto aiAgentMoveDto = new AiAgentMoveDto(aiMove.move(),
                aiMove.confidenceScore());
        
        return ResponseEntity.ok(aiAgentMoveDto);
        }
}

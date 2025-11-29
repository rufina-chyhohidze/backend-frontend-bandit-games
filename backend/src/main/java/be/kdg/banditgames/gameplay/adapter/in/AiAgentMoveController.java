package be.kdg.banditgames.gameplay.adapter.in;


import be.kdg.banditgames.gameplay.adapter.in.request.AiRequest;
import be.kdg.banditgames.gameplay.adapter.in.response.AiAgentResponseDto;
import be.kdg.banditgames.gameplay.port.in.AiMoveMetadata;
import be.kdg.banditgames.gameplay.port.in.AiRequestCommand;
import be.kdg.banditgames.gameplay.port.out.aiAgentMove.AiAgentMoveUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/gameplay/ai-move")
public class AiAgentMoveController {
    
    private final AiAgentMoveUseCase aiAgentMoveUseCase;

    public AiAgentMoveController(AiAgentMoveUseCase aiAgentMoveUseCase) {
        this.aiAgentMoveUseCase = aiAgentMoveUseCase;
    }

    @GetMapping
    public ResponseEntity<AiAgentResponseDto> getBestMove(@RequestBody AiRequest aiRequest) {
        AiMoveMetadata aiMove = aiAgentMoveUseCase.handleMove(
                new AiRequestCommand(
                        aiRequest.gameState(), aiRequest.legalMoves())
        );
        return ResponseEntity.ok(AiAgentResponseDto.fromMetadata(aiMove));
    }
}

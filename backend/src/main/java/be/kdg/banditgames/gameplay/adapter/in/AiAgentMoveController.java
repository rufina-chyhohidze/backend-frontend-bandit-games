package be.kdg.banditgames.gameplay.adapter.in;


import be.kdg.banditgames.common.shared.PlayerType;
import be.kdg.banditgames.gameplay.adapter.in.request.AiRequest;
import be.kdg.banditgames.gameplay.adapter.in.response.AiAgentResponseDto;
import be.kdg.banditgames.gameplay.port.in.AiMoveMetadata;
import be.kdg.banditgames.gameplay.port.in.AiRequestCommand;
import be.kdg.banditgames.gameplay.port.out.aiAgentMove.AiAgentMoveUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/gameplay/ai-move")
public class AiAgentMoveController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final AiAgentMoveUseCase aiAgentMoveUseCase;

    public AiAgentMoveController(AiAgentMoveUseCase aiAgentMoveUseCase) {
        this.aiAgentMoveUseCase = aiAgentMoveUseCase;
    }

    @PostMapping
    public ResponseEntity<AiAgentResponseDto> getBestMove(@RequestBody AiRequest aiRequest) {
        logger.info("getBestMove has been called");
        AiMoveMetadata aiMove = aiAgentMoveUseCase.handleMove(
                new AiRequestCommand(aiRequest.sessionId(), aiRequest.moveNumber(),
                        PlayerType.valueOf(aiRequest.AiType()),
                        aiRequest.gameState(), aiRequest.legalMoves())
        );
        return ResponseEntity.ok(AiAgentResponseDto.fromMetadata(aiMove));
    }
}

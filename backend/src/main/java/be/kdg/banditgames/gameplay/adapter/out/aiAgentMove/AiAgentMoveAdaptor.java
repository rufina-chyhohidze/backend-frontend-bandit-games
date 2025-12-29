package be.kdg.banditgames.gameplay.adapter.out.aiAgentMove;

import be.kdg.banditgames.gameplay.port.in.AiMoveMetadata;
import be.kdg.banditgames.gameplay.port.in.AiRequestCommand;
import be.kdg.banditgames.gameplay.port.out.aiAgentMove.AiAgentMoveService;
import be.kdg.banditgames.gameplay.port.out.aiMetadataPending.SaveAiMetadataPendingPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AiAgentMoveAdaptor implements AiAgentMoveService {
    private static final Logger logger = LoggerFactory.getLogger(AiAgentMoveAdaptor.class);
    private final RestTemplate restTemplate = new RestTemplate();
    private final SaveAiMetadataPendingPort savePendingPort;

    public AiAgentMoveAdaptor(SaveAiMetadataPendingPort savePendingPort) {
        this.savePendingPort = savePendingPort;
    }
    @Override
    public AiMoveMetadata getAiAgentMove(AiRequestCommand aiRequest) {
        String aiApiUrl = "http://localhost:8081/ai";
        String url = aiApiUrl + "/aiMove";

        logger.info("Calling AI service: sessionId={}, moveNumber={}, difficulty={}",
                aiRequest.sessionId(), aiRequest.moveNumber(), aiRequest.playerType());


        AiRequestDto pythonRequest = new AiRequestDto(
                aiRequest.sessionId().toString(),
                aiRequest.moveNumber(),
                aiRequest.playerType().name(),  // AI_EASY, AI_MEDIUM, AI_HARD
                aiRequest.gameState(),
                aiRequest.legalMoves()
        );

        ExternalAiResponse response = restTemplate.postForObject(
                url,
                pythonRequest,
                ExternalAiResponse.class
        );

        if (response == null) {
            throw new IllegalStateException("AI service returned an unexpected null response.");
        }

        logger.info("AI response received: move={}, bestMove={}, confidence={}, visits={}",
                response.move(), response.bestMove(), response.confidence(), response.visits());

        AiMoveMetadata metadata = new AiMoveMetadata(
                response.move(),
                response.confidence(),
                response.bestMove(),
                response.heuristic(),
                response.visits(),
                response.depth()
        );

        // Save snapshot
        savePendingPort.saveTemporaryMetadata(
                aiRequest.sessionId(),
                (aiRequest.moveNumber()+1),
                metadata
        );

        logger.info("Saved AI metadata temporarily for move #{}", aiRequest.moveNumber());

        return metadata;
    }

}

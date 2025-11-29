package be.kdg.banditgames.gameplay.adapter.out.aiAgentMove;

import be.kdg.banditgames.gameplay.adapter.in.response.AiAgentResponseDto;
import be.kdg.banditgames.gameplay.port.in.AiMoveMetadata;
import be.kdg.banditgames.gameplay.port.in.AiRequestCommand;
import be.kdg.banditgames.gameplay.port.out.aiAgentMove.AiAgenteMoveService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AiAgentMoveAdaptor implements AiAgenteMoveService {
    
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public AiMoveMetadata getAiAgentMove(AiRequestCommand aiRequest) {
        String aiApiUrl = "http://localhost:8081/ai";
        String url = aiApiUrl + "/aiMove";


        ExternalAiResponse response = restTemplate.postForObject(
                url,
                aiRequest,
                ExternalAiResponse.class
        );

        if (response == null) {
            throw new IllegalStateException("AI service returned an unexpected null response.");
        }

        return  new AiMoveMetadata(
                response.move(),
                response.confidence(),
                response.bestMove(),
                response.heuristic(),
                response.visits(),
                response.depth()
        );
    }
    
}

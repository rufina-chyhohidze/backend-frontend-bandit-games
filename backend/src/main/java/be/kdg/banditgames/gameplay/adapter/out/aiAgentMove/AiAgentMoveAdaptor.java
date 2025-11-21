package be.kdg.banditgames.gameplay.adapter.out.aiAgentMove;

import be.kdg.banditgames.gameplay.domain.AiMove;
import be.kdg.banditgames.gameplay.port.in.AiRequestCommand;
import be.kdg.banditgames.gameplay.port.out.aiAgentMove.AiAgenteMoveService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AiAgentMoveAdaptor implements AiAgenteMoveService {
    
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public AiMove getAiAgentMove(AiRequestCommand aiRequest) {
        String aiApiUrl = "http://localhost:8081/ai";
        String url = aiApiUrl + "/aiMove";


        AiMove aiMove = restTemplate.postForObject(
            url,
                aiRequest,
            AiMove.class
        );

        if (aiMove == null) {
            throw new IllegalStateException("AI service returned an unexpected null response.");
        }

        return aiMove;
    }
    
}

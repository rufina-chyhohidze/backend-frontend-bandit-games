package be.kdg.banditgames.gameplay.adapter.out.recommendedMove;

import be.kdg.banditgames.gameplay.domain.RecommendedMove;
import be.kdg.banditgames.gameplay.port.out.recommendedMove.RecommendedMovePort;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RecommendedMoveAdaptor implements RecommendedMovePort {
    
    private final RestTemplate restTemplate = new RestTemplate();

    //example of api
    private final String aiApiUrl = "http://localhost:8081/ai";

    @Override
    public RecommendedMove getRecommendedMove(AiRequest aiRequest) {
        String url = aiApiUrl + "/recommend";


        RecommendedMove recommendedMove = restTemplate.postForObject(
            url,
                aiRequest,
            RecommendedMove.class
        );

        if (recommendedMove == null) {
            throw new IllegalStateException("AI service returned an unexpected null response.");
        }

        return recommendedMove;
    }
    
}

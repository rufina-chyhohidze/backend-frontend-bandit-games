package be.kdg.banditgames.gameplay.adapter.out.mlRecommendedMove;

import be.kdg.banditgames.gameplay.domain.RecommendedMove;
import be.kdg.banditgames.gameplay.port.in.MLRecommendedMove.GetRecommendedMoveCommand;
import be.kdg.banditgames.gameplay.port.out.MLRecommendedMove.MLRecommendedMoveService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MLRecommenderMoveAdaptor implements MLRecommendedMoveService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public RecommendedMove getRecommendedMove(GetRecommendedMoveCommand getRecommendedMoveCommand) {
        String url = "http://localhost:8082/ai/aiMove";

        RecommendedMove recommendedMove = restTemplate.postForObject(
                url,
                getRecommendedMoveCommand,
                RecommendedMove.class
        );

        if (recommendedMove == null) {
            throw new IllegalStateException("ML service returned an unexpected null response.");
        }

        return recommendedMove;
    }
}

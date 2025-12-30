package be.kdg.banditgames.gameplay.adapter.out.mlRecommendedMove;

import be.kdg.banditgames.gameplay.domain.RecommendedMove;
import be.kdg.banditgames.gameplay.port.in.MLRecommendedMove.GetRecommendedMoveCommand;
import be.kdg.banditgames.gameplay.port.out.MLRecommendedMove.MLRecommendedMoveService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MLRecommenderMoveAdaptor implements MLRecommendedMoveService {

    private static final Logger logger = LoggerFactory.getLogger(MLRecommenderMoveAdaptor.class);
    private final RestTemplate restTemplate = new RestTemplate();
    private final String mlApiUrl;

    public MLRecommenderMoveAdaptor(@Value("${ml.connect4.api.url}") String mlApiUrl) {
        this.mlApiUrl = mlApiUrl;
    }

    @Override
    public RecommendedMove getRecommendedMove(GetRecommendedMoveCommand getRecommendedMoveCommand) {

        logger.info("Sending request to ML Service [{}]: {}", MLRecommenderMoveAdaptor.this.mlApiUrl, getRecommendedMoveCommand);

        RecommendedMove recommendedMove = restTemplate.postForObject(
                MLRecommenderMoveAdaptor.this.mlApiUrl,
                getRecommendedMoveCommand,
                RecommendedMove.class
        );

        if (recommendedMove == null) {
            logger.error("ML Service returned a NULL body response!");
            throw new IllegalStateException("ML service returned an unexpected null response.");
        }

        logger.info("Received response from ML Service: {}", recommendedMove);

        return recommendedMove;
    }
}
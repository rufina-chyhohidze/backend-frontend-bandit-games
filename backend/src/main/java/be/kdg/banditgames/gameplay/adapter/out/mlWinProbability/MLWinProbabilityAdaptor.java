package be.kdg.banditgames.gameplay.adapter.out.mlWinProbability;

import be.kdg.banditgames.gameplay.domain.WinProbability;
import be.kdg.banditgames.gameplay.port.in.winProbability.GetWinProbabilityCommand;
import be.kdg.banditgames.gameplay.port.out.mlWinProbability.MLWinProbabilityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MLWinProbabilityAdaptor implements MLWinProbabilityService {
    private static final Logger logger = LoggerFactory.getLogger(MLWinProbabilityAdaptor.class);
    private static final String ML_WIN_PROBABILITY_URL = "http://localhost:8082/win-probability";

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public WinProbability getWinProbability(GetWinProbabilityCommand command) {
        logger.info("Calling ML win-probability at {} for session={}, moveNumber={}",
                ML_WIN_PROBABILITY_URL, command.sessionId(), command.moveNumber());

        MLWinProbabilityRequestDto request = new MLWinProbabilityRequestDto(
                command.sessionId().toString(),
                command.moveNumber(),
                command.aiType(),
                command.gameState(),
                command.legalMoves()
        );

        WinProbability response = restTemplate.postForObject(
                ML_WIN_PROBABILITY_URL,
                request,
                WinProbability.class
        );

        if (response == null) {
            throw new IllegalStateException("ML service returned an unexpected null response.");
        }

        logger.info("ML win-probability response: p1={}, p2={}, active={}, distribution size={}",
                response.player1WinProbability(),
                response.player2WinProbability(),
                response.activePlayerWinProbability(),
                response.distribution() != null ? response.distribution().size() : 0);

        return response;
    }

}

package be.kdg.banditgames.gameplay.adapter.out.mlWinProbability;

import be.kdg.banditgames.gameplay.domain.WinProbability;
import be.kdg.banditgames.gameplay.port.in.winProbability.GetWinProbabilityCommand;
import be.kdg.banditgames.gameplay.port.out.mlWinProbability.MLWinProbabilityService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MLWinProbabilityAdaptor implements MLWinProbabilityService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public WinProbability getWinProbability(GetWinProbabilityCommand getWinProbabilityCommand) {
        String url = "http://localhost:8082/win-probability";

        WinProbability winProbabilityDto = restTemplate.postForObject(
                url,
                getWinProbabilityCommand,
                WinProbability.class
        );

        if (winProbabilityDto == null) {
            throw new IllegalStateException("ML service returned an unexpected null response.");
        }

        return winProbabilityDto;
    }

}

package be.kdg.banditgames.gameplay.adapter.out.mlWinProbability;

import be.kdg.banditgames.gameplay.domain.WinProbability;
import be.kdg.banditgames.gameplay.port.in.winProbability.GetWinProbabilityCommand;
import be.kdg.banditgames.gameplay.port.out.mlWinProbability.MLWinProbabilityService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MLWinProbabilityAdaptor implements MLWinProbabilityService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String winProbApiUrl;

    public MLWinProbabilityAdaptor(@Value("${winprob.connect4.api.url}") String winProbApiUrl) {
        this.winProbApiUrl = winProbApiUrl;
    }

    @Override
    public WinProbability getWinProbability(GetWinProbabilityCommand getWinProbabilityCommand) {

        WinProbability winProbabilityDto = restTemplate.postForObject(
                winProbApiUrl,
                getWinProbabilityCommand,
                WinProbability.class
        );

        if (winProbabilityDto == null) {
            throw new IllegalStateException("ML service returned an unexpected null response.");
        }

        return winProbabilityDto;
    }

}

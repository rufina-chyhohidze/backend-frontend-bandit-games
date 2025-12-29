package be.kdg.banditgames.chessACL;

import be.kdg.banditgames.chessACL.requests.RegisterGameRequest;
import be.kdg.banditgames.common.events.generic.GenericAchievementDto;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.UUID;

@Component
public class PlatformGameRegistrationClient {

    private final WebClient webClient;

    public PlatformGameRegistrationClient(WebClient.Builder builder) {
        this.webClient = builder
                .baseUrl("http://localhost:8083")
                .build();
    }

    public void registerGame(
            String name,
            String description,
            String rules,
            String pictureUrl,
            String urlGameSession,
            List<GenericAchievementDto> availableAchievements
    ) {
        webClient.post()
                .uri("/api/dev/games")
                .bodyValue(new RegisterGameRequest(
                        name,
                        description,
                        rules,
                        pictureUrl,
                        urlGameSession,
                        availableAchievements
                ))
                .retrieve()
                .toBodilessEntity()
                .block();
    }
}



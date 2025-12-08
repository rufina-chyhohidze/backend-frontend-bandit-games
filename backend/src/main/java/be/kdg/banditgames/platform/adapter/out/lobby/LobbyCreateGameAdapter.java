package be.kdg.banditgames.platform.adapter.out.lobby;

import be.kdg.banditgames.platform.port.in.lobby.CreateGameCommand;
import be.kdg.banditgames.platform.port.out.lobby.CreateGameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.time.Duration;

@Service
public class LobbyCreateGameAdapter implements CreateGameService {

    private final WebClient webClient;
    private final Logger logger = LoggerFactory.getLogger(LobbyCreateGameAdapter.class);

    public LobbyCreateGameAdapter() {
        this.webClient = WebClient.builder()
                .baseUrl("http://127.0.0.1:8000")
                .build();
    }

    @Override
    public void createGameForLobby(CreateGameCommand createGameCommand) {

        logger.info("Sending POST request to FastAPI /games/create with sessionId {}", createGameCommand.sessionId());

        try {
            webClient.post()
                    .uri("/games/create")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(createGameCommand)
                    .retrieve()
                    .bodyToMono(String.class)
                    .timeout(Duration.ofSeconds(5))
                    .doOnNext(body -> logger.info("FastAPI: Game created successfully, response: {}", body))
                    .block();
        } catch (WebClientResponseException e) {
            logger.error("FastAPI returned {} {} for /games/create",
                    e.getStatusCode(), e.getResponseBodyAsString(), e);
            throw e;
        } catch (Exception e) {
            logger.error("Failed to send POST request to FastAPI", e);
            throw e;
        }
    }
}

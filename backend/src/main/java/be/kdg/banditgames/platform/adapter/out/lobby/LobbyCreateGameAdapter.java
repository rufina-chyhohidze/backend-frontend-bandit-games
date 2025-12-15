package be.kdg.banditgames.platform.adapter.out.lobby;

import be.kdg.banditgames.platform.domain.Game;
import be.kdg.banditgames.platform.port.in.lobby.CreateGameCommand;
import be.kdg.banditgames.platform.port.out.game.LoadPlayableGamesPort;
import be.kdg.banditgames.platform.port.out.lobby.CreateGameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Map;

@Service
public class LobbyCreateGameAdapter implements CreateGameService {

    private final LoadPlayableGamesPort loadPlayableGamesPort;
    private final WebClient webClient;
    private final Logger logger = LoggerFactory.getLogger(LobbyCreateGameAdapter.class);

    // Only backend APIs for games that require a POST (Connect4)
    private static final Map<String, String> BACKEND_API_ENDPOINTS = Map.of(
            "Connect Four", "http://localhost:8000/game/create"
    );

    public LobbyCreateGameAdapter(LoadPlayableGamesPort loadPlayableGamesPort) {
        this.loadPlayableGamesPort = loadPlayableGamesPort;
        this.webClient = WebClient.builder().build();
    }

    @Override
    public void createGameForLobby(CreateGameCommand command) {
        Game game = loadPlayableGamesPort.loadGameById(command.gameId().gameId())
                .orElseThrow(() -> new IllegalStateException("Game not found: " + command.gameId()));

        String endpoint = BACKEND_API_ENDPOINTS.get(game.getName());
        if (endpoint == null) {
            // Chess or other frontend-only games: skip POST
            logger.info("Skipping backend POST for '{}'. Frontend URL: {}", game.getName(), game.getUrlGameSession());
            return;
        }

        logger.info("Creating game at backend endpoint: {} with sessionId {}", endpoint, command.sessionId());

        try {
            String response = webClient.post()
                    .uri(endpoint)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(command)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            logger.info("Game created successfully: {}", response);
        } catch (WebClientResponseException e) {
            logger.error("Error response from game service {}: {}", endpoint, e.getResponseBodyAsString(), e);
            throw e;
        } catch (Exception e) {
            logger.error("Failed to send create game request to {}", endpoint, e);
            throw e;
        }
    }
}

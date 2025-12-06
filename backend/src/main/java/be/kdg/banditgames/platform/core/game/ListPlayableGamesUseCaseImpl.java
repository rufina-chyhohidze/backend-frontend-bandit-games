package be.kdg.banditgames.platform.core.game;

import be.kdg.banditgames.platform.domain.Game;
import be.kdg.banditgames.platform.port.in.game.ListPlayableGamesCommand;
import be.kdg.banditgames.platform.port.in.game.ListPlayableGamesUseCase;
import be.kdg.banditgames.platform.port.in.game.PlayableGameResult;
import be.kdg.banditgames.platform.port.out.game.LoadPlayableGamesPort;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ListPlayableGamesUseCaseImpl implements ListPlayableGamesUseCase {

    private final LoadPlayableGamesPort loadPlayableGamesPort;
    public ListPlayableGamesUseCaseImpl(LoadPlayableGamesPort loadPlayableGamesPort) {
        this.loadPlayableGamesPort = loadPlayableGamesPort;
    }

    @Override
    public List<PlayableGameResult> handle(ListPlayableGamesCommand command) {
        List<Game> games = loadPlayableGamesPort.loadPlayableGames();

        return games.stream()
                .filter(Game::isPlayable)
                .map(g -> new PlayableGameResult(
                        g.getGameId().gameId(),
                        g.getName(),
                        g.getDescription(),
                        g.getPictureUrl(),
                        g.getUrlGameSession()
                ))
                .toList();
    }
}

package be.kdg.banditgames.platform.core.game;

import be.kdg.banditgames.platform.domain.Game;
import be.kdg.banditgames.platform.port.in.game.GameSubmissionCommand;
import be.kdg.banditgames.platform.port.in.game.SubmitGameToPlatformUseCase;
import be.kdg.banditgames.platform.port.out.game.UpdateGamesPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SubmitGameToPlatformUseCaseImpl implements SubmitGameToPlatformUseCase {
    private final List<UpdateGamesPort> updateGamesPort;

    public SubmitGameToPlatformUseCaseImpl(List<UpdateGamesPort> updateGamesPort) {
        this.updateGamesPort = updateGamesPort;
    }

    @Override
    @Transactional
    public Game submitGame(GameSubmissionCommand command) {
        Game game = new Game(
                command.name(),
                command.description(),
                command.rules(),
                command.pictureUrl(),
                command.urlGameSession()
        );

        this.updateGamesPort.forEach(port -> port.updateGames(game));
        return game;
    }
}

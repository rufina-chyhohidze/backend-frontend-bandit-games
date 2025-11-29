package be.kdg.banditgames.platform.core;

import be.kdg.banditgames.platform.domain.Game;
import be.kdg.banditgames.platform.port.in.GameSubmissionCommand;
import be.kdg.banditgames.platform.port.in.SubmitGameToPlatformUseCase;
import be.kdg.banditgames.platform.port.out.UpdateGamesPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class SubmitGameToPlatformUseCaseImpl implements SubmitGameToPlatformUseCase {
    private final UpdateGamesPort updateGamesPort;

    public SubmitGameToPlatformUseCaseImpl(UpdateGamesPort updateGamesPort) {
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

        this.updateGamesPort.updateGames(game);
        return game;
    }
}

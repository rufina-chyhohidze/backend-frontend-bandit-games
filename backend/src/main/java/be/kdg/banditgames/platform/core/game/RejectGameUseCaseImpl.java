package be.kdg.banditgames.platform.core.game;

import be.kdg.banditgames.common.shared.GameId;
import be.kdg.banditgames.platform.domain.Game;
import be.kdg.banditgames.platform.domain.exception.game.GameNotFoundException;
import be.kdg.banditgames.platform.port.in.game.RejectGameCommand;
import be.kdg.banditgames.platform.port.in.game.RejectGameUseCase;
import be.kdg.banditgames.platform.port.out.game.LoadDraftGamesPort;
import be.kdg.banditgames.platform.port.out.game.UpdateGamesPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class RejectGameUseCaseImpl implements RejectGameUseCase {
    private final LoadDraftGamesPort loadDraftGamesPort;
    private final UpdateGamesPort updateGamesPort;

    public RejectGameUseCaseImpl(LoadDraftGamesPort loadDraftGamesPort, UpdateGamesPort updateGamesPort) {
        this.loadDraftGamesPort = loadDraftGamesPort;
        this.updateGamesPort = updateGamesPort;
    }

    @Override
    public Game rejectGame(RejectGameCommand command) {
        Game game = loadDraftGamesPort.findById(GameId.of(command.gameId()))
                .orElseThrow(() -> new GameNotFoundException(command.gameId()));

        game.rejectGame();
        return updateGamesPort.updateGames(game);
    }
}

package be.kdg.banditgames.platform.core.player;

import be.kdg.banditgames.common.shared.GameId;
import be.kdg.banditgames.common.shared.PlayerId;
import be.kdg.banditgames.platform.domain.Game;
import be.kdg.banditgames.platform.domain.Player;
import be.kdg.banditgames.platform.domain.exception.game.GameNotFoundException;
import be.kdg.banditgames.platform.domain.exception.game.GameNotPlayableException;
import be.kdg.banditgames.platform.domain.exception.player.PlayerNotFoundException;
import be.kdg.banditgames.platform.port.in.player.AddFavoriteGameCommand;
import be.kdg.banditgames.platform.port.in.player.AddFavoriteGameUseCase;
import be.kdg.banditgames.platform.port.out.game.LoadPlayableGamesPort;
import be.kdg.banditgames.platform.port.out.player.LoadPlayerPort;
import be.kdg.banditgames.platform.port.out.player.SavePlayerPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AddFavoriteGameUseCaseImpl implements AddFavoriteGameUseCase {
    private final LoadPlayerPort loadPlayerPort;
    private final SavePlayerPort savePlayerPort;
    private final LoadPlayableGamesPort loadPlayableGamesPort;

    public AddFavoriteGameUseCaseImpl(LoadPlayerPort loadPlayerPort, SavePlayerPort savePlayerPort, LoadPlayableGamesPort loadPlayableGamesPort) {
        this.loadPlayerPort = loadPlayerPort;
        this.savePlayerPort = savePlayerPort;
        this.loadPlayableGamesPort = loadPlayableGamesPort;
    }

    @Override
    public Player addToFavorites(AddFavoriteGameCommand command) {
        PlayerId playerId = command.playerId();
        GameId gameId = command.gameId();

        Player player = loadPlayerPort.loadById(playerId)
                .orElseThrow(() -> new PlayerNotFoundException(playerId.playerId()));

        Game game = loadPlayableGamesPort.loadGameById(gameId.gameId())
                .orElseThrow(() -> new GameNotFoundException(gameId.gameId()));

        if (!game.isPlayable()) {
            throw new GameNotPlayableException(gameId.gameId());
        }

        player.addFavoriteGame(game.getGameId());

        savePlayerPort.save(player);

        return player;
    }
}

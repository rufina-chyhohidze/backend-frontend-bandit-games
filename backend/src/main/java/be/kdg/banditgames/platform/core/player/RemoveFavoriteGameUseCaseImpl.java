package be.kdg.banditgames.platform.core.player;

import be.kdg.banditgames.common.shared.GameId;
import be.kdg.banditgames.common.shared.PlayerId;
import be.kdg.banditgames.platform.domain.Player;
import be.kdg.banditgames.platform.domain.exception.player.PlayerNotFoundException;
import be.kdg.banditgames.platform.port.in.player.RemoveFavoriteGameCommand;
import be.kdg.banditgames.platform.port.in.player.RemoveFavoriteGameUseCase;
import be.kdg.banditgames.platform.port.out.player.LoadPlayerPort;
import be.kdg.banditgames.platform.port.out.player.SavePlayerPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class RemoveFavoriteGameUseCaseImpl implements RemoveFavoriteGameUseCase {
    private final LoadPlayerPort loadPlayerPort;
    private final SavePlayerPort savePlayerPort;

    public RemoveFavoriteGameUseCaseImpl(LoadPlayerPort loadPlayerPort,
                                         SavePlayerPort savePlayerPort) {
        this.loadPlayerPort = loadPlayerPort;
        this.savePlayerPort = savePlayerPort;
    }

    @Override
    public Player removeFromFavorites(RemoveFavoriteGameCommand command) {
        PlayerId playerId = command.playerId();
        GameId gameId = command.gameId();

        Player player = loadPlayerPort.loadById(playerId)
                .orElseThrow(() -> new PlayerNotFoundException(playerId.playerId()));

        player.removeFavoriteGame(gameId);

        savePlayerPort.save(player);

        return player;
    }


}

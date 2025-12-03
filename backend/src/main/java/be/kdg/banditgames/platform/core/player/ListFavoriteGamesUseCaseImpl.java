package be.kdg.banditgames.platform.core.player;

import be.kdg.banditgames.common.shared.PlayerId;
import be.kdg.banditgames.platform.domain.Player;
import be.kdg.banditgames.platform.domain.exception.player.PlayerNotFoundException;
import be.kdg.banditgames.platform.port.in.game.PlayableGameResult;
import be.kdg.banditgames.platform.port.in.player.ListFavoriteGamesCommand;
import be.kdg.banditgames.platform.port.in.player.ListFavoriteGamesUseCase;
import be.kdg.banditgames.platform.port.out.game.LoadGamesByIdsPort;
import be.kdg.banditgames.platform.port.out.player.LoadPlayerPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class ListFavoriteGamesUseCaseImpl implements ListFavoriteGamesUseCase {
    private final LoadPlayerPort loadPlayerPort;
    private final LoadGamesByIdsPort loadGamesByIdsPort;

    public ListFavoriteGamesUseCaseImpl(
            LoadPlayerPort loadPlayerPort,
            LoadGamesByIdsPort loadGamesByIdsPort
    ) {
        this.loadPlayerPort = loadPlayerPort;
        this.loadGamesByIdsPort = loadGamesByIdsPort;
    }

    @Override
    public List<PlayableGameResult> list(ListFavoriteGamesCommand command) {
        PlayerId playerId = command.playerId();

        Player player = loadPlayerPort.loadById(playerId)
                .orElseThrow(() -> new PlayerNotFoundException(playerId.playerId()));

        List<UUID> ids = player.getFavoriteGames()
                .stream()
                .map(g -> g.gameId())
                .toList();

        return loadGamesByIdsPort.loadGamesByIds(ids)
                .stream()
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

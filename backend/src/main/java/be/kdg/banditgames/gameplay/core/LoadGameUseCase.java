package be.kdg.banditgames.gameplay.core;

import be.kdg.banditgames.common.shared.GameId;
import be.kdg.banditgames.gameplay.domain.GameProjection;
import be.kdg.banditgames.gameplay.port.in.game.LoadGameByNamePort;
import be.kdg.banditgames.gameplay.port.out.game.LoadGameProjectionPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class LoadGameUseCase implements LoadGameByNamePort {
    private final LoadGameProjectionPort gamePort;

    public LoadGameUseCase(LoadGameProjectionPort gamePort) {
        this.gamePort = gamePort;
    }

    @Override
    public GameId findByName(String name) {
        var gameProjection = gamePort.findByName(name);
        return gameProjection.map(projection -> GameId.of(projection.gameId())).orElse(null);
    }
}

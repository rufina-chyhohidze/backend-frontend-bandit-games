package be.kdg.banditgames.platform.port.out.game;

import be.kdg.banditgames.platform.domain.Game;

import java.util.List;
import java.util.UUID;

public interface LoadGamesByIdsPort {
    List<Game> loadGamesByIds(List<UUID> ids);
}

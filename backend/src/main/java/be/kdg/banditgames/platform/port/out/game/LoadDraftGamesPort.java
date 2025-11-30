package be.kdg.banditgames.platform.port.out.game;

import be.kdg.banditgames.common.shared.GameId;
import be.kdg.banditgames.platform.domain.Game;

import java.util.List;
import java.util.Optional;

public interface LoadDraftGamesPort {
    Optional<Game> findById(GameId gameId);
    List<Game> findByStatusPendingApproval();
}

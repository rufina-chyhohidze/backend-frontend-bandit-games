package be.kdg.banditgames.platform.port.out.player;

import be.kdg.banditgames.common.shared.PlayerId;
import be.kdg.banditgames.platform.domain.Player;

import java.util.List;
import java.util.Optional;

public interface LoadPlayerPort {
    Optional<Player> loadById(PlayerId id);
    List<Player> findByUsernameContainingIgnoreCase(String usernamePart);
}


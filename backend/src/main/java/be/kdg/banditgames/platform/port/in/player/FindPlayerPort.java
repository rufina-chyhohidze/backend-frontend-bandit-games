package be.kdg.banditgames.platform.port.in.player;

import be.kdg.banditgames.platform.domain.Player;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FindPlayerPort {
    Optional<Player> findById(UUID playerId);
    List<Player> findByUsername(String usernamePart);
}

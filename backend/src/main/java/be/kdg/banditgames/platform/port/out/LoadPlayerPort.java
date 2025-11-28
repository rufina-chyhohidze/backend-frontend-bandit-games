package be.kdg.banditgames.platform.port.out;

import be.kdg.banditgames.common.shared.PlayerId;
import be.kdg.banditgames.platform.domain.Player;

import java.util.Optional;

public interface LoadPlayerPort {
    Optional<Player> loadById(PlayerId id);
}


package be.kdg.banditgames.platform.port.out;

import be.kdg.banditgames.platform.domain.Player;

public interface SavePlayerPort {
    void save(Player player);
}

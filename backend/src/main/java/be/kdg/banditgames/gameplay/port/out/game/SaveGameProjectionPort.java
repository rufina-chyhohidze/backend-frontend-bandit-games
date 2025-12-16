package be.kdg.banditgames.gameplay.port.out.game;

import java.util.UUID;

public interface SaveGameProjectionPort {
    void saveGameProjection(UUID gameId, String name);
}

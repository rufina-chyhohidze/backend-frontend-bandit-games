package be.kdg.banditgames.gameplay.port.in.game;

import java.util.UUID;

public interface CreateGamePort {
    void createGame(String name, UUID gameId);
}

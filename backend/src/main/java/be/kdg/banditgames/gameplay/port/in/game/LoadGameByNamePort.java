package be.kdg.banditgames.gameplay.port.in.game;

import be.kdg.banditgames.common.shared.GameId;

import java.util.UUID;

public interface LoadGameByNamePort {
    GameId findByName(String name);

}

package be.kdg.banditgames.gameplay.port.out.game;

import be.kdg.banditgames.gameplay.domain.GameProjection;

import java.util.Optional;


public interface LoadGameProjectionPort {
    Optional<GameProjection> findByName(String name);

}

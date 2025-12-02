package be.kdg.banditgames.platform.port.in.player;

import be.kdg.banditgames.platform.domain.Player;

public interface PlayerCreationUseCase {

    Player createPlayer(CreatePlayerCommand command);
}

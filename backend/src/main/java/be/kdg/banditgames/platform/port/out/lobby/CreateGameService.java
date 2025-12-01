package be.kdg.banditgames.platform.port.out.lobby;

import be.kdg.banditgames.platform.port.in.lobby.CreateGameCommand;

public interface CreateGameService {

    void createGameForLobby(CreateGameCommand createGameCommand);

}

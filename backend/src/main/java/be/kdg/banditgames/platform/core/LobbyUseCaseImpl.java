package be.kdg.banditgames.platform.core;

import be.kdg.banditgames.common.shared.GameId;
import be.kdg.banditgames.common.shared.PlayerId;
import be.kdg.banditgames.common.shared.PlayerType;
import be.kdg.banditgames.platform.domain.Game;
import be.kdg.banditgames.platform.domain.Lobby;
import be.kdg.banditgames.platform.domain.exception.PlayerAlreadyInLobbyException;
import be.kdg.banditgames.platform.domain.vo.LobbyId;
import be.kdg.banditgames.platform.port.in.lobby.CreateLobbyCommand;
import be.kdg.banditgames.platform.port.in.lobby.LobbyCreationUseCase;
import be.kdg.banditgames.platform.port.in.lobby.ManagingLobbyUseCase;
import be.kdg.banditgames.platform.port.out.LoadPlayableGamesPort;
import be.kdg.banditgames.platform.port.out.lobby.LoadLobbyPort;
import be.kdg.banditgames.platform.port.out.lobby.LobbyLookupPort;
import be.kdg.banditgames.platform.port.out.lobby.PersistLobbyPort;
import org.springframework.stereotype.Service;

@Service
public class LobbyUseCaseImpl implements LobbyCreationUseCase, ManagingLobbyUseCase {
    
    private final LoadLobbyPort loadLobbyPort;
    private final PersistLobbyPort persistLobbyPort;
    private final LobbyLookupPort lobbyLookupPort;
    private final LoadPlayableGamesPort loadPlayableGamesPort;
    
    public LobbyUseCaseImpl(LoadLobbyPort loadLobbyPort, 
                            PersistLobbyPort persistLobbyPort, 
                            LobbyLookupPort lobbyLookup,
                            LoadPlayableGamesPort loadPlayableGamesPort) {
        this.loadLobbyPort = loadLobbyPort;
        this.persistLobbyPort = persistLobbyPort;
        this.lobbyLookupPort = lobbyLookup;
        this.loadPlayableGamesPort = loadPlayableGamesPort;
    }

    @Override
    public Lobby createLobby(CreateLobbyCommand createLobbyCommand) {
        PlayerId playerId = PlayerId.of(createLobbyCommand.playerId());

        if (lobbyLookupPort.isPlayerInAnyLobby(playerId)) {
            throw new PlayerAlreadyInLobbyException(playerId);
        }
        
        Lobby lobby = Lobby.createNew(playerId);
        persistLobbyPort.saveLobby(lobby);
        return lobby;
    }

    @Override
    public void closeLobby(LobbyId lobbyId) {
        persistLobbyPort.removeLobby(lobbyId);
    }

    @Override
    public Lobby addPlayerToLobby(PlayerId playerId, LobbyId lobbyId) {
        if (lobbyLookupPort.isPlayerInAnyLobby(playerId)) {
            throw new PlayerAlreadyInLobbyException(playerId);
        }

        Lobby lobby = loadLobbyPort.loadLobbyById(lobbyId)
                .orElseThrow();
        lobby.changeGuest(playerId, PlayerType.HUMAN);
        persistLobbyPort.saveLobby(lobby);
        return lobby;
    }

    @Override
    public void removePlayerFromLobby(PlayerId playerId, LobbyId lobbyId) {
        Lobby lobby = loadLobbyPort.loadLobbyById(lobbyId)
                .orElseThrow();
        lobby.removePlayer(playerId);
        persistLobbyPort.saveLobby(lobby);
    }

    @Override
    public String startGameInLobby(LobbyId lobbyId) {
        Lobby lobby = loadLobbyPort.loadLobbyById(lobbyId)
                .orElseThrow();

        Game game = loadPlayableGamesPort.loadGameById(
                lobby.getGameId().gameId()).orElseThrow();
        
        lobby.startGame();
        persistLobbyPort.saveLobby(lobby);
        return game.getUrlGameSession();
    }

    @Override
    public void chooseGameForLobby(LobbyId lobbyId, GameId gameId) {
        Lobby lobby = loadLobbyPort.loadLobbyById(lobbyId)
                .orElseThrow();
        lobby.chooseGame(gameId);
        persistLobbyPort.saveLobby(lobby);
    }
}

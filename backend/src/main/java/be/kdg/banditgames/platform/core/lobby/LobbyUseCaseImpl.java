package be.kdg.banditgames.platform.core.lobby;

import be.kdg.banditgames.common.shared.GameId;
import be.kdg.banditgames.common.shared.PlayerId;
import be.kdg.banditgames.common.shared.PlayerType;
import be.kdg.banditgames.platform.adapter.in.response.StartGameResponse;
import be.kdg.banditgames.platform.domain.Game;
import be.kdg.banditgames.platform.domain.Lobby;
import be.kdg.banditgames.platform.domain.exception.lobby.PlayerAlreadyInLobbyException;
import be.kdg.banditgames.platform.domain.vo.LobbyId;
import be.kdg.banditgames.platform.port.in.lobby.CreateGameCommand;
import be.kdg.banditgames.platform.port.in.lobby.CreateLobbyCommand;
import be.kdg.banditgames.platform.port.in.lobby.LobbyCreationUseCase;
import be.kdg.banditgames.platform.port.in.lobby.ManagingLobbyUseCase;
import be.kdg.banditgames.platform.port.out.game.LoadPlayableGamesPort;
import be.kdg.banditgames.platform.port.out.lobby.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class LobbyUseCaseImpl implements LobbyCreationUseCase, ManagingLobbyUseCase, FindLobbyPort {
    
    private final LoadLobbyPort loadLobbyPort;
    private final PersistLobbyPort persistLobbyPort;
    private final LobbyLookupPort lobbyLookupPort;
    private final LoadPlayableGamesPort loadPlayableGamesPort;
    private final CreateGameService createGameService;
    
    public LobbyUseCaseImpl(LoadLobbyPort loadLobbyPort, 
                            PersistLobbyPort persistLobbyPort, 
                            LobbyLookupPort lobbyLookup,
                            LoadPlayableGamesPort loadPlayableGamesPort,
                            CreateGameService createGameService) {
        this.loadLobbyPort = loadLobbyPort;
        this.persistLobbyPort = persistLobbyPort;
        this.lobbyLookupPort = lobbyLookup;
        this.loadPlayableGamesPort = loadPlayableGamesPort;
        this.createGameService = createGameService;
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
    public StartGameResponse startGameInLobby(LobbyId lobbyId) {
        Lobby lobby = loadLobbyPort.loadLobbyById(lobbyId)
                .orElseThrow();

        Game game = loadPlayableGamesPort.loadGameById(
                lobby.getGameId().gameId()).orElseThrow();
        
        
        if(!lobby.hasStartedGame()){
            createGameService.createGameForLobby(
                    new CreateGameCommand(
                            lobbyId.lobbyID(),
                            lobby.getHostPlayer().playerId(),
                            lobby.getGuestPlayer().playerId(),
                            lobby.getHostType(),
                            lobby.getGuestType()
                    )
            );
        }

        lobby.startGame();
        persistLobbyPort.saveLobby(lobby);
        
        String hostUrl = String.format(
                "%s?sessionId=%s&playerId=%s",
                game.getUrlGameSession(),
                lobbyId.lobbyID(),
                lobby.getHostPlayer().playerId()
        );

        String guestUrl = String.format(
                "%s?sessionId=%s&playerId=%s",
                game.getUrlGameSession(),
                lobbyId.lobbyID(),
                lobby.getGuestPlayer().playerId()
        );
        
        return new StartGameResponse(
                hostUrl,
                guestUrl,
                lobby.getHostType().name(),
                lobby.getGuestType().name()
        );
    }

    @Override
    public void chooseGameForLobby(LobbyId lobbyId, GameId gameId) {
        Lobby lobby = loadLobbyPort.loadLobbyById(lobbyId)
                .orElseThrow();
        lobby.chooseGame(gameId);
        persistLobbyPort.saveLobby(lobby);
    }

    @Override
    public Lobby findLobbyById(UUID lobbyId) {
        return loadLobbyPort.loadLobbyById(LobbyId.of(lobbyId))
                .orElseThrow();
    }

    @Override
    public Optional<Lobby> findLobbyByPlayerId(UUID playerId) {
        return loadLobbyPort.loadLobbyByPlayerId(PlayerId.of(playerId));
    }

    @Override
    public List<Lobby> findLobbies() {
        return loadLobbyPort.loadAll();
    }
}

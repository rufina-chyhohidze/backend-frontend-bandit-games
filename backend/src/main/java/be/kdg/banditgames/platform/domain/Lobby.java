package be.kdg.banditgames.platform.domain;

import be.kdg.banditgames.common.shared.GameId;
import be.kdg.banditgames.common.shared.PlayerType;
import be.kdg.banditgames.common.shared.PlayerId;
import be.kdg.banditgames.platform.domain.vo.LobbyId;

public class Lobby {

    private final LobbyId lobbyId;
    private PlayerId hostPlayer;
    private PlayerType hostType;
    private PlayerId guestPlayer;
    private PlayerType guestType;
    private LobbyStatus lobbyStatus;
    private GameId gameId;

    private Lobby(LobbyId lobbyId,
                  PlayerId hostPlayer,
                  PlayerType hostType,
                  PlayerId guestPlayer,
                  PlayerType guestType,
                  LobbyStatus lobbyStatus,
                  GameId gameId) {

        this.lobbyId = lobbyId;
        this.hostPlayer = hostPlayer;
        this.guestPlayer = guestPlayer;
        this.hostType = hostType;
        this.guestType = guestType;
        this.lobbyStatus = lobbyStatus;
        this.gameId = gameId;
    }

    public static Lobby createNew(PlayerId hostPlayerId) {
        return new Lobby(LobbyId.create(), hostPlayerId,  PlayerType.HUMAN,null, null, LobbyStatus.WAITING, null);
    }

    public static Lobby rehydrate(LobbyId lobbyId,
                                  PlayerId hostPlayer,
                                  PlayerType hostType,
                                  PlayerId guestPlayer,
                                  PlayerType guestType,
                                  LobbyStatus lobbyStatus,
                                  GameId gameId) {
        return new Lobby(lobbyId, hostPlayer, hostType, guestPlayer, guestType, lobbyStatus, gameId);
    }

    public LobbyId getLobbyId() {
        return lobbyId;
    }

    public PlayerId getHostPlayer() {
        return hostPlayer;
    }

    public PlayerId getGuestPlayer() {
        return guestPlayer;
    }

    public PlayerType getHostType() {
        return hostType;
    }

    public PlayerType getGuestType() {
        return guestType;
    }
    
    public void removePlayer(PlayerId playerId) {
        if (playerId.equals(hostPlayer)) {
            this.hostPlayer = null;
            this.hostType = null;
        } else if (playerId.equals(guestPlayer)) {
            this.guestPlayer = null;
            this.guestType = null;
        }
    }

    public void changeGuest(PlayerId newGuest, PlayerType type) {
        this.guestPlayer = newGuest;
        this.guestType = type;
    }

    public void changeHost(PlayerId newHost, PlayerType type) {
        this.hostPlayer = newHost;
        this.hostType = type;
    }
    
    public void changeGuestToAIPlayerEasy() {
        this.guestPlayer = null;
        this.guestType = PlayerType.AI_EASY;
    }

    public void changeGuestToAIPlayerMedium() {
        this.guestPlayer = null;
        this.guestType = PlayerType.AI_MEDIUM;
    }    
    
    public void changeGuestToAIPlayerHard() {
        this.guestPlayer = null;
        this.guestType = PlayerType.AI_HARD;
    }
    public LobbyStatus getLobbyStatus() {
        return lobbyStatus;
    }

    public void startGame() {
        this.lobbyStatus = LobbyStatus.IN_GAME;
    }
    
    public GameId getGameId() {
        return gameId;
    }

    public void chooseGame(GameId gameId) {
        this.gameId = gameId;
    }

    public boolean hasStartedGame() {
        return lobbyStatus == LobbyStatus.IN_GAME;
    }
}

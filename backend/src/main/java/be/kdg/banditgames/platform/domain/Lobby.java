package be.kdg.banditgames.platform.domain;

import be.kdg.banditgames.common.shared.PlayerType;
import be.kdg.banditgames.common.shared.PlayerId;
import be.kdg.banditgames.platform.domain.vo.LobbyId;

public class Lobby {

    private final LobbyId lobbyId;
    private PlayerId hostPlayer;
    private PlayerType hostType;
    private PlayerId guestPlayer;
    private PlayerType guestType;

    private Lobby(LobbyId lobbyId,
                  PlayerId hostPlayer,
                  PlayerId guestPlayer,
                  PlayerType hostType,
                  PlayerType guestType) {

        this.lobbyId = lobbyId;
        this.hostPlayer = hostPlayer;
        this.guestPlayer = guestPlayer;
        this.hostType = hostType;
        this.guestType = guestType;
    }

    public static Lobby createNew(PlayerId hostPlayer,
                                  PlayerId guestPlayer,
                                  PlayerType hostType,
                                  PlayerType guestType) {
        return new Lobby(LobbyId.create(), hostPlayer, guestPlayer, hostType, guestType);
    }

    public static Lobby rehydrate(LobbyId lobbyId,
                                  PlayerId hostPlayer,
                                  PlayerId guestPlayer,
                                  PlayerType hostType,
                                  PlayerType guestType) {
        return new Lobby(lobbyId, hostPlayer, guestPlayer, hostType, guestType);
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
}

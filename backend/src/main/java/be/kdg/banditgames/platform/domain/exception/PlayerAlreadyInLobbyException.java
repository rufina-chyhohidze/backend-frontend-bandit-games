package be.kdg.banditgames.platform.domain.exception;


import be.kdg.banditgames.common.shared.PlayerId;

public class PlayerAlreadyInLobbyException extends RuntimeException {
    private final PlayerId playerId;

    public PlayerAlreadyInLobbyException(PlayerId playerId) {
        super("Player " + playerId + " is already in a lobby.");
        this.playerId = playerId;
    }

    public PlayerId getPlayerId() {
        return playerId;
    }
}

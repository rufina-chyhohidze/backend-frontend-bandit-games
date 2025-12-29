package be.kdg.banditgames.platform.domain.exception.lobby;

public class LobbyFullException extends RuntimeException {

    public LobbyFullException(String message) {
        super(message);
    }

    public LobbyFullException(String message, Throwable cause) {
        super(message, cause);
    }
}
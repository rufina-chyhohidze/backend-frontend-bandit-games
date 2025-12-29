package be.kdg.banditgames.platform.domain.exception.lobby;

public class LobbyNotFoundException extends RuntimeException {
    public LobbyNotFoundException(String message) {
        super(message);
    }
    public LobbyNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
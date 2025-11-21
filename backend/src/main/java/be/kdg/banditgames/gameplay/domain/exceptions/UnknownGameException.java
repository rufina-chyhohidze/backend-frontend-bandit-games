package be.kdg.banditgames.gameplay.domain.exceptions;

public class UnknownGameException extends RuntimeException {
    public UnknownGameException(String message) {
        super(message);
    }
}

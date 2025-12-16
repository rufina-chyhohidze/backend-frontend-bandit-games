package be.kdg.banditgames.platform.adapter.in.error;

import be.kdg.banditgames.platform.domain.exception.lobby.PlayerAlreadyInLobbyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(PlayerAlreadyInLobbyException.class)
    public ResponseEntity<ApiError> handlePlayerAlreadyInLobby(PlayerAlreadyInLobbyException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ApiError("You are already in a lobby. Leave your current lobby before inviting someone."));
    }

    public record ApiError(String message) {}
}

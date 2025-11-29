package be.kdg.banditgames.platform.adapter.in;

import be.kdg.banditgames.common.shared.GameId;
import be.kdg.banditgames.common.shared.PlayerId;
import be.kdg.banditgames.platform.adapter.in.requests.CreateLobbyRequest;
import be.kdg.banditgames.platform.adapter.in.response.LobbyDto;
import be.kdg.banditgames.platform.adapter.in.response.LobbyDtoMapper;
import be.kdg.banditgames.platform.domain.Lobby;
import be.kdg.banditgames.platform.domain.vo.LobbyId;
import be.kdg.banditgames.platform.port.in.lobby.CreateLobbyCommand;
import be.kdg.banditgames.platform.port.in.lobby.LobbyCreationUseCase;
import be.kdg.banditgames.platform.port.in.lobby.ManagingLobbyUseCase;
import be.kdg.banditgames.platform.port.out.lobby.FindLobbyPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/lobby")
public class LobbyController {

    private final LobbyCreationUseCase lobbyCreationUseCase;
    private final ManagingLobbyUseCase managingLobbyUseCase;
    private final FindLobbyPort findLobbyUseCase;

    public LobbyController(LobbyCreationUseCase lobbyCreationUseCase,
                           ManagingLobbyUseCase managingLobbyUseCase,
                           FindLobbyPort findLobbyUseCase) {
        this.lobbyCreationUseCase = lobbyCreationUseCase;
        this.managingLobbyUseCase = managingLobbyUseCase;
        this.findLobbyUseCase = findLobbyUseCase;
    }

    @GetMapping("/by-id")
    public ResponseEntity<LobbyDto> getLobby(@RequestParam UUID lobbyId) {
        Lobby lobby = findLobbyUseCase.findLobbyById(lobbyId);
        return ResponseEntity.ok(LobbyDtoMapper.toDto(lobby));
    }
    @GetMapping("/by-player")
    public ResponseEntity<LobbyDto> getLobbyByPlayerId(@AuthenticationPrincipal Jwt principal) {
        UUID playerId = UUID.fromString(principal.getSubject());

        Optional<Lobby> lobbyOpt = findLobbyUseCase.findLobbyByPlayerId(playerId);

        return lobbyOpt
                .map(lobby -> ResponseEntity.ok(LobbyDtoMapper.toDto(lobby)))
                .orElseGet(() -> ResponseEntity.ok(null));
    }

    @PostMapping("/create")
    public ResponseEntity<LobbyDto> createLobby(
            @AuthenticationPrincipal Jwt principal,
            @RequestBody(required = false) CreateLobbyRequest ignoredRequest
    ) {
        UUID playerId = UUID.fromString(principal.getSubject());

        Lobby lobby = lobbyCreationUseCase.createLobby(
                new CreateLobbyCommand(playerId)
        );

        return ResponseEntity.ok(LobbyDtoMapper.toDto(lobby));
    }

    @PostMapping("/{lobbyId}/add-player")
    public ResponseEntity<LobbyDto> addPlayerToLobby(
            @PathVariable UUID lobbyId,
            @AuthenticationPrincipal Jwt principal
    ) {
        UUID playerId = UUID.fromString(principal.getSubject());

        Lobby lobby = managingLobbyUseCase.addPlayerToLobby(
                PlayerId.of(playerId),
                LobbyId.of(lobbyId)
        );

        return ResponseEntity.ok(LobbyDtoMapper.toDto(lobby));
    }

    @DeleteMapping("/{lobbyId}")
    public ResponseEntity<Void> closeLobby(
            @PathVariable UUID lobbyId
    ) {
        lobbyCreationUseCase.closeLobby(LobbyId.of(lobbyId));
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{lobbyId}/leave-lobby")
    public ResponseEntity<Void> leaveLobby(
            @PathVariable UUID lobbyId,
            @AuthenticationPrincipal Jwt principal
    ) {
        UUID playerId = UUID.fromString(principal.getSubject());

        managingLobbyUseCase.removePlayerFromLobby(
                PlayerId.of(playerId),
                LobbyId.of(lobbyId)
        );

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{lobbyId}/start-game")
    public ResponseEntity<Void> startGame(
            @PathVariable UUID lobbyId
    ) {
        String redirectUrl = managingLobbyUseCase.startGameInLobby(LobbyId.of(lobbyId));

        return ResponseEntity.status(HttpStatus.SEE_OTHER)
                .location(URI.create(redirectUrl))
                .build();
    }

    @PostMapping("/{lobbyId}/choose-game")
    public ResponseEntity<Void> chooseGame(
            @PathVariable UUID lobbyId,
            @RequestParam UUID gameId
    ) {
        managingLobbyUseCase.chooseGameForLobby(
                LobbyId.of(lobbyId),
                GameId.of(gameId)
        );
        return ResponseEntity.noContent().build();
    }
}

package be.kdg.banditgames.platform.adapter.in;

import be.kdg.banditgames.common.shared.GameId;
import be.kdg.banditgames.common.shared.PlayerId;
import be.kdg.banditgames.common.shared.PlayerType;
import be.kdg.banditgames.platform.adapter.in.requests.CreateLobbyRequest;
import be.kdg.banditgames.platform.adapter.in.response.LobbyDto;
import be.kdg.banditgames.platform.adapter.in.response.LobbyDtoMapper;
import be.kdg.banditgames.platform.adapter.in.response.StartGameResponse;
import be.kdg.banditgames.platform.domain.Lobby;
import be.kdg.banditgames.platform.domain.vo.LobbyId;
import be.kdg.banditgames.platform.port.in.lobby.CreateLobbyCommand;
import be.kdg.banditgames.platform.port.in.lobby.LobbyCreationUseCase;
import be.kdg.banditgames.platform.port.in.lobby.ManagingLobbyUseCase;
import be.kdg.banditgames.platform.port.in.lobby.FindLobbyPort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    
    @GetMapping("/all")
    public ResponseEntity<List<LobbyDto>> getAllLobbies() {
        List<Lobby> lobbies = findLobbyUseCase.findLobbies();
        List<LobbyDto> lobbyDtos = lobbies.stream()
                .map(LobbyDtoMapper::toDto)
                .toList();
        return ResponseEntity.ok(lobbyDtos);
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
    public ResponseEntity<StartGameResponse> startGame(
            @PathVariable UUID lobbyId
    ) {
        StartGameResponse response = managingLobbyUseCase.startGameInLobby(LobbyId.of(lobbyId));

        return ResponseEntity
                .ok()
                .body(response);
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

    @PostMapping("/{lobbyId}/choose-ai")
    public ResponseEntity<LobbyDto> chooseAiOpponent(
            @PathVariable UUID lobbyId,
            @RequestParam("difficulty") String difficulty,
            @AuthenticationPrincipal Jwt principal
    ) {
        UUID playerId = UUID.fromString(principal.getSubject());

        PlayerType aiType = switch (difficulty.toUpperCase()) {
            case "EASY" -> PlayerType.AI_EASY;
            case "MEDIUM" -> PlayerType.AI_MEDIUM;
            case "HARD" -> PlayerType.AI_HARD;
            case "ML" -> PlayerType.AI_ML;
            default -> throw new IllegalArgumentException("Unknown AI difficulty: " + difficulty);
        };

        managingLobbyUseCase.chooseAiOpponent(
                LobbyId.of(lobbyId),
                PlayerId.of(playerId),
                aiType
        );

        Lobby updated = findLobbyUseCase.findLobbyById(lobbyId);
        return ResponseEntity.ok(LobbyDtoMapper.toDto(updated));
    }
}

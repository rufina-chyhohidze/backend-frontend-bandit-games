package be.kdg.banditgames.platform.adapter.in;

import be.kdg.banditgames.common.shared.PlayerId;
import be.kdg.banditgames.platform.adapter.in.requests.CreateLobbyRequest;
import be.kdg.banditgames.platform.adapter.in.response.LobbyDto;
import be.kdg.banditgames.platform.adapter.in.response.LobbyDtoMapper;
import be.kdg.banditgames.platform.domain.Lobby;
import be.kdg.banditgames.platform.domain.vo.LobbyId;
import be.kdg.banditgames.platform.port.in.lobby.CreateLobbyCommand;
import be.kdg.banditgames.platform.port.in.lobby.LobbyCreationUseCase;
import be.kdg.banditgames.platform.port.in.lobby.ManagingLobbyUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/lobby")
public class LobbyController {

    private final LobbyCreationUseCase lobbyCreationUseCase;
    private final ManagingLobbyUseCase managingLobbyUseCase;

    public LobbyController(LobbyCreationUseCase lobbyCreationUseCase, ManagingLobbyUseCase managingLobbyUseCase) {
        this.lobbyCreationUseCase = lobbyCreationUseCase;
        this.managingLobbyUseCase = managingLobbyUseCase;
    }

    @PostMapping("/create")
    public ResponseEntity<LobbyDto> createLobby(
            @RequestBody CreateLobbyRequest createLobbyRequest
    ) {
        Lobby lobby = lobbyCreationUseCase.createLobby(new CreateLobbyCommand((createLobbyRequest.playerId())));
        LobbyDto lobbyDto = LobbyDtoMapper.toDto(lobby);
        return ResponseEntity.ok(lobbyDto);
    }

    @PostMapping("/{lobbyId}/add-player")
    public ResponseEntity<Lobby> addPlayerToLobby(@PathVariable UUID lobbyId, @RequestParam UUID playerId) {
        Lobby lobby = managingLobbyUseCase.addPlayerToLobby(PlayerId.of(playerId), LobbyId.of(lobbyId));
        return ResponseEntity.ok(lobby);
    }

    @DeleteMapping("/{lobbyId}")
    public ResponseEntity<Void> closeLobby(@PathVariable UUID lobbyId) {
        lobbyCreationUseCase.closeLobby(LobbyId.of(lobbyId));
        return ResponseEntity.noContent().build();
    }
}

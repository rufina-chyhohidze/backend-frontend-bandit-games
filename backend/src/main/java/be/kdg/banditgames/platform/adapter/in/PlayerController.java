package be.kdg.banditgames.platform.adapter.in;

import be.kdg.banditgames.platform.adapter.in.response.PlayerDto;
import be.kdg.banditgames.platform.domain.Player;
import be.kdg.banditgames.platform.port.in.player.CreatePlayerCommand;
import be.kdg.banditgames.platform.port.in.player.FindPlayerPort;
import be.kdg.banditgames.platform.port.in.player.PlayerCreationUseCase;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/player")
public class PlayerController {
    private final PlayerCreationUseCase playerCreationUseCase;
    private final FindPlayerPort findPlayerPort;

    public PlayerController(PlayerCreationUseCase playerCreationUseCase,
                            FindPlayerPort findPlayerPort) {
        this.playerCreationUseCase = playerCreationUseCase;
        this.findPlayerPort = findPlayerPort;
    }

    @PostMapping("/register")
    @PreAuthorize("hasAuthority('player')")
    public PlayerDto register(@AuthenticationPrincipal Jwt jwt) {
        Player player = getOrCreatePlayer(jwt);
        return PlayerDto.fromDomain(player);
    }

    @GetMapping("/me")
    @PreAuthorize("hasAuthority('player')")
    public PlayerDto me(@AuthenticationPrincipal Jwt jwt) {
        Player player = getOrCreatePlayer(jwt);
        return PlayerDto.fromDomain(player);
    }

    @GetMapping("/search")
    @PreAuthorize("hasAuthority('player')")
    public List<PlayerDto> searchByUsername(@RequestParam String username) {
        return findPlayerPort.findByUsername(username).stream()
                .map(PlayerDto::fromDomain)
                .toList();
    }

    private Player getOrCreatePlayer(Jwt jwt) {
        UUID keycloakId = UUID.fromString(jwt.getSubject());
        String username = Optional.ofNullable(jwt.getClaimAsString("preferred_username"))
                .orElse(jwt.getClaimAsString("email"));

        CreatePlayerCommand command = new CreatePlayerCommand(keycloakId, username);

        return findPlayerPort.findById(keycloakId)
                .orElseGet(() -> playerCreationUseCase.createPlayer(command));
    }
}

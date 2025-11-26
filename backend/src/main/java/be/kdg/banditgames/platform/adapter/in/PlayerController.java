package be.kdg.banditgames.platform.adapter.in;

import be.kdg.banditgames.gameplay.domain.vo.PlayerId;
import be.kdg.banditgames.platform.adapter.in.dto.PlayerDto;
import be.kdg.banditgames.platform.domain.Player;
import be.kdg.banditgames.platform.port.out.LoadPlayerPort;
import be.kdg.banditgames.platform.port.out.SavePlayerPort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/player")
public class PlayerController {
    private final LoadPlayerPort loadPlayerPort;
    private final SavePlayerPort savePlayerPort;

    public PlayerController(LoadPlayerPort loadPlayerPort, SavePlayerPort savePlayerPort) {
        this.loadPlayerPort = loadPlayerPort;
        this.savePlayerPort = savePlayerPort;
    }

    /**
     * First call for a user with Keycloak role "player".
     * If a Player with this ID exists, returns it.
     * Otherwise creates and saves a new Player.
     */
    @PostMapping("/register")
    @PreAuthorize("hasAuthority('player')")
    public PlayerDto register(@AuthenticationPrincipal Jwt jwt) {
        UUID keycloakId = UUID.fromString(jwt.getSubject());
        String username = Optional.ofNullable(jwt.getClaimAsString("preferred_username"))
                .orElse(jwt.getClaimAsString("email"));

        PlayerId playerId = PlayerId.of(keycloakId);

        Player player = loadPlayerPort.loadById(playerId)
                .orElseGet(() -> {
                    Player newPlayer = new Player(playerId, username);
                    savePlayerPort.save(newPlayer);
                    return newPlayer;
                });

        return PlayerDto.fromDomain(player);
    }

    /**
     * Returns the current player's info.
     * Also ensures there is a Player in the DB (same as register).
     */
    @GetMapping("/me")
    @PreAuthorize("hasAuthority('player')")
    public PlayerDto me(@AuthenticationPrincipal Jwt jwt) {
        UUID keycloakId = UUID.fromString(jwt.getSubject());
        String username = Optional.ofNullable(jwt.getClaimAsString("preferred_username"))
                .orElse(jwt.getClaimAsString("email"));

        PlayerId playerId = PlayerId.of(keycloakId);

        Player player = loadPlayerPort.loadById(playerId)
                .orElseGet(() -> {
                    Player newPlayer = new Player(playerId, username);
                    savePlayerPort.save(newPlayer);
                    return newPlayer;
                });

        return PlayerDto.fromDomain(player);
    }
}

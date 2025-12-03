package be.kdg.banditgames.platform.adapter.in;

import be.kdg.banditgames.common.shared.GameId;
import be.kdg.banditgames.common.shared.PlayerId;
import be.kdg.banditgames.platform.adapter.in.response.PlayerDto;
import be.kdg.banditgames.platform.domain.Player;
import be.kdg.banditgames.platform.port.in.game.PlayableGameResult;
import be.kdg.banditgames.platform.port.in.player.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final AddFavoriteGameUseCase addFavoriteGameUseCase;
    private final RemoveFavoriteGameUseCase removeFavoriteGameUseCase;
    private final ListFavoriteGamesUseCase listFavoriteGamesUseCase;

    private final Logger logger = LoggerFactory.getLogger(PlayerController.class);

    public PlayerController(PlayerCreationUseCase playerCreationUseCase,
                            FindPlayerPort findPlayerPort,AddFavoriteGameUseCase addFavoriteGameUseCase,RemoveFavoriteGameUseCase removeFavoriteGameUseCase,ListFavoriteGamesUseCase listFavoriteGamesUseCase) {
        this.playerCreationUseCase = playerCreationUseCase;
        this.findPlayerPort = findPlayerPort;
        this.addFavoriteGameUseCase = addFavoriteGameUseCase;
        this.removeFavoriteGameUseCase = removeFavoriteGameUseCase;
        this.listFavoriteGamesUseCase = listFavoriteGamesUseCase;
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
    @PostMapping("/favorites/{gameId}")
    @PreAuthorize("hasAuthority('player')")
    public PlayerDto addFavorite(@PathVariable UUID gameId,
                                 @AuthenticationPrincipal Jwt jwt) {
        UUID keycloakId = UUID.fromString(jwt.getSubject());
        PlayerId playerId = PlayerId.of(keycloakId);
        GameId gid = GameId.of(gameId);
        logger.info("Adding favorite game: {}", gid + "for " + playerId);

        AddFavoriteGameCommand command = new AddFavoriteGameCommand(playerId, gid);
        Player updated = addFavoriteGameUseCase.addToFavorites(command);

        return PlayerDto.fromDomain(updated);
    }
    @DeleteMapping("/favorites/{gameId}")
    @PreAuthorize("hasAuthority('player')")
    public PlayerDto removeFavorite(@PathVariable UUID gameId,
                                    @AuthenticationPrincipal Jwt jwt) {
        UUID keycloakId = UUID.fromString(jwt.getSubject());
        PlayerId playerId = PlayerId.of(keycloakId);
        GameId gid = GameId.of(gameId);

        logger.info("Removing favorite game: {}", gid + "for " + playerId);
        RemoveFavoriteGameCommand command = new RemoveFavoriteGameCommand(playerId, gid);
        Player updated = removeFavoriteGameUseCase.removeFromFavorites(command);
        return PlayerDto.fromDomain(updated);
    }

    @GetMapping("/favorites")
    @PreAuthorize("hasAuthority('player')")
    public List<PlayableGameResult> favorites(@AuthenticationPrincipal Jwt jwt) {
        UUID keycloakId = UUID.fromString(jwt.getSubject());
        var command = new ListFavoriteGamesCommand(PlayerId.of(keycloakId));
        logger.info("Listing favorite game: {}", command + "for " + PlayerId.of(keycloakId));
        return listFavoriteGamesUseCase.list(command);
    }
}

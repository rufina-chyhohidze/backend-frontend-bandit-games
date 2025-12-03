package be.kdg.banditgames.platform.adapter.in;

import be.kdg.banditgames.platform.adapter.in.response.AdminGameDto;
import be.kdg.banditgames.platform.domain.Game;
import be.kdg.banditgames.platform.port.in.game.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
@RestController
@RequestMapping("/api/admin/games")
public class AdminGameModerationController {

    private static final Logger log = LoggerFactory.getLogger(AdminGameModerationController.class);

    private final ApproveGameUseCase approveGameUseCase;
    private final RejectGameUseCase rejectGameUseCase;
    private final ListPendingGamesUseCase listPendingGamesUseCase;

    public AdminGameModerationController(ApproveGameUseCase approveGameUseCase,
                                         RejectGameUseCase rejectGameUseCase,
                                         ListPendingGamesUseCase listPendingGamesUseCase) {
        this.approveGameUseCase = approveGameUseCase;
        this.rejectGameUseCase = rejectGameUseCase;
        this.listPendingGamesUseCase = listPendingGamesUseCase;
    }

    @GetMapping("/pending")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<List<AdminGameDto>> listPendingGames() {
        List<Game> games = listPendingGamesUseCase.listPendingGames();
        List<AdminGameDto> dtos = games.stream()
                .map(game -> new AdminGameDto(
                        game.getGameId().gameId(),
                        game.getName(),
                        game.getDescription(),
                        game.getRules(),
                        game.getPictureUrl(),
                        game.getUrlGameSession(),
                        game.getStatus()
                ))
                .toList();
        log.info("Games listed: {}", dtos);
        return ResponseEntity.ok(dtos);
    }

    @PostMapping("/{gameId}/approve")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<AdminGameDto> approve(@PathVariable UUID gameId) {
        Game game = approveGameUseCase.approveGame(new ApproveGameCommand(gameId));

        AdminGameDto dto = new AdminGameDto(
                game.getGameId().gameId(),
                game.getName(),
                game.getDescription(),
                game.getRules(),
                game.getPictureUrl(),
                game.getUrlGameSession(),
                game.getStatus()
        );
        log.info("Game approved: {}", dto);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/{gameId}/reject")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<AdminGameDto> reject(@PathVariable UUID gameId) {
        Game game = rejectGameUseCase.rejectGame(new RejectGameCommand(gameId));

        AdminGameDto dto = new AdminGameDto(
                game.getGameId().gameId(),
                game.getName(),
                game.getDescription(),
                game.getRules(),
                game.getPictureUrl(),
                game.getUrlGameSession(),
                game.getStatus()
        );
        log.info("Game rejected: {}", dto);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/draft")
    @PreAuthorize("hasAuthority('admin')")
    public List<AdminGameDto> getDraftGames() {
        return listPendingGamesUseCase.listPendingGames()
                .stream()
                .map(AdminGameDto::fromDomain)
                .toList();
    }

}



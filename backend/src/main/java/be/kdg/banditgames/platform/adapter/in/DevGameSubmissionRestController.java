package be.kdg.banditgames.platform.adapter.in;

import be.kdg.banditgames.platform.adapter.in.requests.CreateGameRequest;
import be.kdg.banditgames.platform.adapter.in.response.GameDto;
import be.kdg.banditgames.platform.domain.Game;
import be.kdg.banditgames.platform.port.in.GameSubmissionCommand;
import be.kdg.banditgames.platform.port.in.SubmitGameToPlatformUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dev/games")
public class DevGameSubmissionRestController {
    private final SubmitGameToPlatformUseCase submitGameToPlatformUseCase;

    public DevGameSubmissionRestController(SubmitGameToPlatformUseCase submitGameToPlatformUseCase) {
        this.submitGameToPlatformUseCase = submitGameToPlatformUseCase;
    }

    @PostMapping
    public ResponseEntity<GameDto> submitGame(@RequestBody CreateGameRequest request) {
        GameSubmissionCommand command = new GameSubmissionCommand(
                request.name(),
                request.description(),
                request.rules(),
                request.pictureUrl(),
                request.urlGameSession()
        );

        Game game = submitGameToPlatformUseCase.submitGame(command);

        return ResponseEntity.ok(new GameDto(
                game.getName(),
                game.getDescription(),
                game.getRules(),
                game.getPictureUrl(),
                game.getUrlGameSession()
        ));
    }
}

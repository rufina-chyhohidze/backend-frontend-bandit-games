package be.kdg.banditgames.platform.adapter.in;

import be.kdg.banditgames.platform.port.in.ListPlayableGamesCommand;
import be.kdg.banditgames.platform.port.in.ListPlayableGamesUseCase;
import be.kdg.banditgames.platform.port.in.PlayableGameResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/games")
public class GameController {
    private final ListPlayableGamesUseCase useCase;

    public GameController(ListPlayableGamesUseCase useCase) {
        this.useCase = useCase;
    }

    @GetMapping
    public List<PlayableGameResult> listPlayableGames() {
        return useCase.handle(new ListPlayableGamesCommand());
    }
}

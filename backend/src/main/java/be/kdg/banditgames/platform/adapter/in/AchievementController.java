package be.kdg.banditgames.platform.adapter.in;

import be.kdg.banditgames.common.shared.GameId;
import be.kdg.banditgames.platform.port.in.AvailableAchievementResult;
import be.kdg.banditgames.platform.port.in.ListAvailableAchievementsCommand;
import be.kdg.banditgames.platform.port.in.ListAvailableAchievementsUseCase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/games/{gameId}/achievements")
public class AchievementController {
    private final ListAvailableAchievementsUseCase listAvailableAchievementsUseCase;

    public AchievementController(ListAvailableAchievementsUseCase listAvailableAchievementsUseCase) {
        this.listAvailableAchievementsUseCase = listAvailableAchievementsUseCase;
    }

    @GetMapping
    public List<AvailableAchievementResult> getAvailableAchievements(@PathVariable UUID gameId) {
        var command = new ListAvailableAchievementsCommand(GameId.of(gameId));
        return listAvailableAchievementsUseCase.listAvailableAchievements(command);
    }
}

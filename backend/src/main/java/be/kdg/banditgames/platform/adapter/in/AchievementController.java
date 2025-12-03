package be.kdg.banditgames.platform.adapter.in;

import be.kdg.banditgames.common.shared.GameId;
import be.kdg.banditgames.platform.port.in.achievement.AvailableAchievementResult;
import be.kdg.banditgames.platform.port.in.achievement.ListAvailableAchievementsCommand;
import be.kdg.banditgames.platform.port.in.achievement.ListAvailableAchievementsUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/games/{gameId}/achievements")
public class AchievementController {
    private static final Logger log = LoggerFactory.getLogger(AchievementController.class);

    private final ListAvailableAchievementsUseCase listAvailableAchievementsUseCase;

    public AchievementController(ListAvailableAchievementsUseCase listAvailableAchievementsUseCase) {
        this.listAvailableAchievementsUseCase = listAvailableAchievementsUseCase;
    }

    @GetMapping
    public List<AvailableAchievementResult> getAvailableAchievements(@PathVariable UUID gameId) {
        log.debug("Retrieving available achievement results");
        var command = new ListAvailableAchievementsCommand(GameId.of(gameId));
        return listAvailableAchievementsUseCase.listAvailableAchievements(command);
    }
}

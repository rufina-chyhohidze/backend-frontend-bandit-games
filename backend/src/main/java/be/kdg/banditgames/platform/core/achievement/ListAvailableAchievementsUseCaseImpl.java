package be.kdg.banditgames.platform.core.achievement;

import be.kdg.banditgames.platform.domain.Achievement;
import be.kdg.banditgames.platform.port.in.achievement.AvailableAchievementResult;
import be.kdg.banditgames.platform.port.in.achievement.ListAvailableAchievementsCommand;
import be.kdg.banditgames.platform.port.in.achievement.ListAvailableAchievementsUseCase;
import be.kdg.banditgames.platform.port.out.achievement.LoadAvailableAchievementsPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ListAvailableAchievementsUseCaseImpl implements ListAvailableAchievementsUseCase {
    private final LoadAvailableAchievementsPort loadAvailableAchievementsPort;

    public ListAvailableAchievementsUseCaseImpl(LoadAvailableAchievementsPort loadAvailableAchievementsPort) {
        this.loadAvailableAchievementsPort = loadAvailableAchievementsPort;
    }

    @Override
    public List<AvailableAchievementResult> listAvailableAchievements(ListAvailableAchievementsCommand command) {
        List<Achievement> achievements =
                loadAvailableAchievementsPort.loadAvailableAchievements(command.gameId());

        return achievements.stream()
                .map(AvailableAchievementResult::fromDomain)
                .toList();
    }

}

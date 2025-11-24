package be.kdg.banditgames.platform.core;

import be.kdg.banditgames.platform.domain.Achievement;
import be.kdg.banditgames.platform.port.in.AvailableAchievementResult;
import be.kdg.banditgames.platform.port.in.ListAvailableAchievementsCommand;
import be.kdg.banditgames.platform.port.in.ListAvailableAchievementsUseCase;
import be.kdg.banditgames.platform.port.out.LoadAvailableAchievementsPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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

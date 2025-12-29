package be.kdg.banditgames.platform.core.achievement;

import be.kdg.banditgames.platform.domain.Player;
import be.kdg.banditgames.platform.domain.exception.player.PlayerNotFoundException;
import be.kdg.banditgames.platform.port.in.achievement.AwardAchievementCommand;
import be.kdg.banditgames.platform.port.in.achievement.AwardAchievementUseCase;
import be.kdg.banditgames.platform.port.out.player.LoadPlayerPort;
import be.kdg.banditgames.platform.port.out.player.SavePlayerPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AwardAchievementUseCaseImpl implements AwardAchievementUseCase {
    private final LoadPlayerPort loadPlayerPort;
    private final SavePlayerPort savePlayerPort;

    public AwardAchievementUseCaseImpl(LoadPlayerPort loadPlayerPort, SavePlayerPort savePlayerPort) {
        this.loadPlayerPort = loadPlayerPort;
        this.savePlayerPort = savePlayerPort;
    }

    @Override
    public void awardAchievement(AwardAchievementCommand command) {
        Player player = loadPlayerPort.loadById(command.playerId())
                .orElseThrow(() -> new PlayerNotFoundException(command.playerId().playerId()));

        player.addAchievement(command.achievementId());

        savePlayerPort.save(player);
    }


}

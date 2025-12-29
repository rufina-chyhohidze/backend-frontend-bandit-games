package be.kdg.banditgames.platform.core.game;

import be.kdg.banditgames.common.events.generic.GenericAchievementDto;
import be.kdg.banditgames.platform.domain.Achievement;
import be.kdg.banditgames.platform.domain.Game;
import be.kdg.banditgames.platform.port.in.game.GameSubmissionCommand;
import be.kdg.banditgames.platform.port.in.game.SubmitGameToPlatformUseCase;
import be.kdg.banditgames.platform.port.out.achievement.UpdateAchievementsPort;
import be.kdg.banditgames.platform.port.out.game.UpdateGamesPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SubmitGameToPlatformUseCaseImpl implements SubmitGameToPlatformUseCase {
    private final List<UpdateGamesPort> updateGamesPort;
    private final UpdateAchievementsPort updateAchievementsPort;

    public SubmitGameToPlatformUseCaseImpl(List<UpdateGamesPort> updateGamesPort, UpdateAchievementsPort updateAchievementsPort) {
        this.updateGamesPort = updateGamesPort;
        this.updateAchievementsPort = updateAchievementsPort;
    }

    @Override
    @Transactional
    public Game submitGame(GameSubmissionCommand command) {
        Game game = new Game(
                command.name(),
                command.description(),
                command.rules(),
                command.pictureUrl(),
                command.urlGameSession()
        );

        this.updateGamesPort.forEach(port -> port.updateGames(game));

        for(GenericAchievementDto dto : command.availableAchievements()) {
            Achievement achievement = new Achievement(
                    game.getGameId(),
                    dto.code(),
                    dto.description(),
                    null
            );

            updateAchievementsPort.save(achievement);
        }

        return game;
    }
}

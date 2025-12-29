package be.kdg.banditgames.platform.port.in.game;

import be.kdg.banditgames.platform.domain.Game;

public interface SubmitGameToPlatformUseCase {
    Game submitGame(GameSubmissionCommand command);
}

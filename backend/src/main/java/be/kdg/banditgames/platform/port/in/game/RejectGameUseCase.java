package be.kdg.banditgames.platform.port.in.game;

import be.kdg.banditgames.platform.domain.Game;

public interface RejectGameUseCase {
    Game rejectGame(RejectGameCommand command);
}

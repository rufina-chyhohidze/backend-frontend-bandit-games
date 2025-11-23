package be.kdg.banditgames.platform.port.in;

import java.util.List;

public interface ListPlayableGamesUseCase {
    List<PlayableGameResult> handle(ListPlayableGamesCommand command);
}

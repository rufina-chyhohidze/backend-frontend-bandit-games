package be.kdg.banditgames.platform.port.in.player;

import be.kdg.banditgames.platform.port.in.game.PlayableGameResult;

import java.util.List;
import java.util.UUID;

public interface ListFavoriteGamesUseCase {
    List<PlayableGameResult> list(ListFavoriteGamesCommand command);
}

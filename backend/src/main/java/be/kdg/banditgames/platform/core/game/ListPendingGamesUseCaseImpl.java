package be.kdg.banditgames.platform.core.game;

import be.kdg.banditgames.platform.domain.Game;
import be.kdg.banditgames.platform.port.in.game.ListPendingGamesUseCase;
import be.kdg.banditgames.platform.port.out.game.LoadDraftGamesPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ListPendingGamesUseCaseImpl implements ListPendingGamesUseCase {
    private final LoadDraftGamesPort loadDraftGamesPort;

    public ListPendingGamesUseCaseImpl(LoadDraftGamesPort loadDraftGamesPort) {
        this.loadDraftGamesPort = loadDraftGamesPort;
    }

    @Override
    public List<Game> listPendingGames() {
        return loadDraftGamesPort.findByStatusPendingApproval();
    }
}

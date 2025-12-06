package be.kdg.banditgames.gameplay.core;

import be.kdg.banditgames.gameplay.domain.GameSession;
import be.kdg.banditgames.gameplay.domain.exceptions.GameSesssionNotFound;
import be.kdg.banditgames.gameplay.port.in.GameResultsCommand;
import be.kdg.banditgames.gameplay.port.in.GameResultsPort;
import be.kdg.banditgames.gameplay.port.out.gameSession.LoadGameSessionPort;
import be.kdg.banditgames.gameplay.port.out.gameSession.PersistGameSessionPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class GameResultsUseCaseImpl implements GameResultsPort {
    private final PersistGameSessionPort persistGameSessionPort;
    private final LoadGameSessionPort loadGameSessionPort;
    public GameResultsUseCaseImpl(PersistGameSessionPort persistGameSessionPort, LoadGameSessionPort loadGameSessionPort) {
        this.persistGameSessionPort = persistGameSessionPort;
        this.loadGameSessionPort = loadGameSessionPort;
    }


    @Override
    public void finishGame(GameResultsCommand command) {

        GameSession gameSession = loadGameSessionPort.loadGameSessionById(command.sessionId()).orElseThrow(() -> new GameSesssionNotFound("Game session not found: " +  command.sessionId()));
        gameSession.finishGame(command.gameResult(), command.occurredAt());

        persistGameSessionPort.markCompleted(gameSession.getSessionsId(), gameSession.getGameResult(), gameSession.getEndTime());
    }
}

package be.kdg.banditgames.gameplay.core;

import be.kdg.banditgames.gameplay.port.in.GameResultsCommand;
import be.kdg.banditgames.gameplay.port.in.GameResultsPort;
import be.kdg.banditgames.gameplay.port.out.gameSession.PersistGameSessionPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class GameResultsUseCaseImpl implements GameResultsPort {
    private final PersistGameSessionPort persistGameSessionPort;
    public GameResultsUseCaseImpl(PersistGameSessionPort persistGameSessionPort) {
        this.persistGameSessionPort = persistGameSessionPort;
    }


    @Override
    public void finishGame(GameResultsCommand command) {

        //TODO save to the db without breaking the MongoDB

    }
}

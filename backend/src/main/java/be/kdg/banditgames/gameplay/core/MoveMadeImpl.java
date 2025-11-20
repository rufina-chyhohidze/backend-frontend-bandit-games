package be.kdg.banditgames.gameplay.core;

import be.kdg.banditgames.gameplay.domain.GameState;
import be.kdg.banditgames.gameplay.domain.vo.SessionId;
import be.kdg.banditgames.gameplay.port.in.MoveMadeCommand;
import be.kdg.banditgames.gameplay.port.in.MoveMadePort;
import be.kdg.banditgames.gameplay.port.out.gameSession.PersistGameSessionPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MoveMadeImpl implements MoveMadePort {
    
    private final PersistGameSessionPort persistGameSessionPort;

    public MoveMadeImpl(PersistGameSessionPort persistGameSessionPort) {
        this.persistGameSessionPort = persistGameSessionPort;
    }

    @Override
    public void project(MoveMadeCommand moveMadeCommand) {
        GameState gameState = GameState.createNew(
                SessionId.of(moveMadeCommand.gameId()),
                moveMadeCommand.playerType(),
                moveMadeCommand.playerSide(),
                moveMadeCommand.moveNumber(),
                moveMadeCommand.serializedBoard(),
                moveMadeCommand.serializedLegalMoves()
        );
        persistGameSessionPort.addGameState(gameState.getSessionId(), gameState);
    }
}

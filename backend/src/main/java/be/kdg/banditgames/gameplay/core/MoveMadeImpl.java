package be.kdg.banditgames.gameplay.core;

import be.kdg.banditgames.gameplay.domain.AiMove;
import be.kdg.banditgames.gameplay.domain.GameState;
import be.kdg.banditgames.gameplay.domain.vo.SessionId;
import be.kdg.banditgames.gameplay.port.in.AiRequestCommand;
import be.kdg.banditgames.gameplay.port.in.MoveMadeCommand;
import be.kdg.banditgames.gameplay.port.in.MoveMadePort;
import be.kdg.banditgames.gameplay.port.out.aiAgentMove.AiAgentMoveUseCase;
import be.kdg.banditgames.gameplay.port.out.gameSession.PersistGameSessionPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MoveMadeImpl implements MoveMadePort {
    
    private final PersistGameSessionPort persistGameSessionPort;
    private final AiAgentMoveUseCase aiAgentMoveUseCase;

    public MoveMadeImpl(PersistGameSessionPort persistGameSessionPort, AiAgentMoveUseCase aiAgentMoveUseCase) {
        this.persistGameSessionPort = persistGameSessionPort;
        this.aiAgentMoveUseCase = aiAgentMoveUseCase;
    }

    @Override
    public void project(MoveMadeCommand moveMadeCommand) {
        GameState gameState = GameState.createNew(
                moveMadeCommand.playerType(),
                moveMadeCommand.playerSide(),
                moveMadeCommand.moveNumber(),
                moveMadeCommand.serializedBoard(),
                moveMadeCommand.serializedLegalMoves());

        // Call AI service to evaluate
        AiMove aiMove = aiAgentMoveUseCase.handleMove(
                new AiRequestCommand(moveMadeCommand.serializedBoard(),
                        moveMadeCommand.serializedLegalMoves())
        );

        persistGameSessionPort.addGameState(SessionId.of(moveMadeCommand.gameId()), gameState, aiMove);    }
}


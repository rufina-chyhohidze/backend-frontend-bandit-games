package be.kdg.banditgames.gameplay.core;

import be.kdg.banditgames.gameplay.domain.GameSession;
import be.kdg.banditgames.gameplay.domain.GameState;
import be.kdg.banditgames.common.shared.SessionId;
import be.kdg.banditgames.gameplay.port.in.AiTurn.ProcessAiTurnCommand;
import be.kdg.banditgames.gameplay.port.in.AiTurn.ProcessAiTurnPort;
import be.kdg.banditgames.gameplay.port.in.MoveMadeCommand;
import be.kdg.banditgames.gameplay.port.in.MoveMadePort;
import be.kdg.banditgames.gameplay.port.out.gameSession.LoadGameSessionPort;
import be.kdg.banditgames.gameplay.port.out.gameSession.PersistGameSessionPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MoveMadeImpl implements MoveMadePort {
    private static final Logger logger = LoggerFactory.getLogger(MoveMadeImpl.class);
    private final PersistGameSessionPort persistGameSessionPort;
    private final LoadGameSessionPort loadGameSessionPort;
    private final ProcessAiTurnPort processAiTurnPort;

    public MoveMadeImpl(PersistGameSessionPort persistGameSessionPort, LoadGameSessionPort loadGameSessionPort, ProcessAiTurnPort processAiTurnPort) {
        this.persistGameSessionPort = persistGameSessionPort;
        this.loadGameSessionPort = loadGameSessionPort;
        this.processAiTurnPort = processAiTurnPort;
    }

    @Override
    public void project(MoveMadeCommand moveMadeCommand) {
        logger.info("Processing move for session: {}, player: {}, move: {}",
                moveMadeCommand.sessionId(), moveMadeCommand.playerSide(), moveMadeCommand.moveNumber());

        GameState gameState = GameState.createNew(
                moveMadeCommand.playerType(),
                moveMadeCommand.playerSide(),
                moveMadeCommand.moveNumber(),
                moveMadeCommand.serializedBoard(),
                moveMadeCommand.serializedLegalMoves());

        SessionId sessionId = SessionId.of(moveMadeCommand.sessionId());
        persistGameSessionPort.addGameState(sessionId, gameState, null);

        logger.info("Saved move #{} to database", moveMadeCommand.moveNumber());

        GameSession session = loadGameSessionPort.loadGameSessionById(sessionId)
                .orElseThrow(() -> new IllegalStateException("Game session not found: " + sessionId));

        if (session.isNextPlayerAi()) {
            logger.info("Next player is AI, triggering AI turn");
            processAiTurnPort.processAiTurn(new ProcessAiTurnCommand(moveMadeCommand.sessionId(),moveMadeCommand.serializedBoard(), moveMadeCommand.serializedLegalMoves()));
        } else {
            logger.info("Next player is HUMAN, waiting for their move");
        }
    }
}

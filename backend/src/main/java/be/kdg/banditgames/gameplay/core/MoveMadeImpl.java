package be.kdg.banditgames.gameplay.core;

import be.kdg.banditgames.gameplay.domain.GameSession;
import be.kdg.banditgames.gameplay.domain.GameState;
import be.kdg.banditgames.common.shared.SessionId;
import be.kdg.banditgames.gameplay.domain.exceptions.GameSesssionNotFound;
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

    public MoveMadeImpl(PersistGameSessionPort persistGameSessionPort, LoadGameSessionPort loadGameSessionPort) {
        this.persistGameSessionPort = persistGameSessionPort;
        this.loadGameSessionPort = loadGameSessionPort;
    }

    @Override
    public void project(MoveMadeCommand moveMadeCommand) {
        logger.info("Processing move for session: {}, player: {}, move: {}",
                moveMadeCommand.sessionId(), moveMadeCommand.playerSide(), moveMadeCommand.moveNumber());

        GameState gameState = GameState.createNewWithAiMetadata(
                moveMadeCommand.playerType(),
                moveMadeCommand.playerSide(),
                moveMadeCommand.moveNumber(),
                moveMadeCommand.serializedBoard(),
                moveMadeCommand.serializedLegalMoves(),
                moveMadeCommand.actualMove(),
                moveMadeCommand.aiHardRecommendedMove(),
                moveMadeCommand.aiHardConfidence(),
                moveMadeCommand.aiHardWinProbability(),
                moveMadeCommand.aiMlRecommendedMove(),
                moveMadeCommand.aiMlConfidence(),
                moveMadeCommand.aiMlWinProbability());

        GameSession gameSession = loadGameSessionPort.loadGameSessionById(SessionId.of(moveMadeCommand.sessionId())).orElseThrow(() -> new GameSesssionNotFound("Game session not found: " +  SessionId.of(moveMadeCommand.sessionId())));

        persistGameSessionPort.appendMove(gameSession.getSessionsId(), gameState);

    }
}

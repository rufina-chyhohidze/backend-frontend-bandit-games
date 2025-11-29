package be.kdg.banditgames.gameplay.core;

import be.kdg.banditgames.common.shared.SessionId;
import be.kdg.banditgames.gameplay.domain.GameSession;
import be.kdg.banditgames.gameplay.domain.GameState;
import be.kdg.banditgames.gameplay.port.in.AiMoveMetadata;
import be.kdg.banditgames.gameplay.port.in.AiRequestCommand;
import be.kdg.banditgames.gameplay.port.in.AiTurn.ProcessAiTurnCommand;
import be.kdg.banditgames.gameplay.port.in.AiTurn.ProcessAiTurnPort;
import be.kdg.banditgames.gameplay.port.out.aiAgentMove.AiAgentMoveUseCase;
import be.kdg.banditgames.gameplay.port.out.gameSession.LoadGameSessionPort;
import be.kdg.banditgames.gameplay.port.out.gameSession.PersistGameSessionPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProcessAiTurnImpl implements ProcessAiTurnPort {
    
    private static final Logger logger = LoggerFactory.getLogger(ProcessAiTurnImpl.class);
    
    private final LoadGameSessionPort loadGameSessionPort;
    private final PersistGameSessionPort persistGameSessionPort;
    private final AiAgentMoveUseCase aiAgentMoveUseCase;

    public ProcessAiTurnImpl(LoadGameSessionPort loadGameSessionPort,
                             PersistGameSessionPort persistGameSessionPort,
                             AiAgentMoveUseCase aiAgentMoveUseCase) {
        this.loadGameSessionPort = loadGameSessionPort;
        this.persistGameSessionPort = persistGameSessionPort;
        this.aiAgentMoveUseCase = aiAgentMoveUseCase;
    }

    @Override
    public void processAiTurn(ProcessAiTurnCommand command) {
        SessionId sessionId = SessionId.of(command.sessionId());
        
        GameSession session = loadGameSessionPort.loadGameSessionById(sessionId)
                .orElseThrow(() -> new IllegalStateException("Game session not found"));
        
        int nextMoveNumber = session.getGameStates().size() + 1;
        
        logger.info("AI processing turn #{} for session: {}", nextMoveNumber, sessionId);

        AiMoveMetadata aiMetadata = aiAgentMoveUseCase.handleMove(
                new AiRequestCommand(command.serializedBoard(), command.legalMoves())
        );
        
        logger.info("AI recommended move: {} with confidence: {}", 
                    aiMetadata.recommendedMove(), aiMetadata.confidenceScore());

        // TODO: correct whit the application of the AI
        // Apply AI's move to create new board state
        //String newBoardState = applyMove(command.serializedBoard(), aiMetadata.recommendedMove());
        // will assume at the moment that the recommendedMove is the seriealiseBoard already with the move applied
        String newBoardState = aiMetadata.recommendedMove();

        GameState aiGameState = GameState.createNew(
                session.getCurrentPlayerType(),
                session.getCurrentTurn(),
                nextMoveNumber,
                newBoardState,
                ""
        );

        persistGameSessionPort.addGameState(sessionId, aiGameState, aiMetadata);
        
        logger.info("Saved AI move #{} to database", nextMoveNumber);

        session = loadGameSessionPort.loadGameSessionById(sessionId)
                .orElseThrow(() -> new IllegalStateException("Game session not found"));
        
        if (session.isNextPlayerAi()) {
            logger.info("Next player is also AI, triggering another AI turn");
            processAiTurn(command);
        }
    }

}
package be.kdg.banditgames.gameplay.core;

import be.kdg.banditgames.gameplay.domain.GameSession;
import be.kdg.banditgames.common.shared.GameId;
import be.kdg.banditgames.common.shared.SessionId;
import be.kdg.banditgames.gameplay.port.in.gameSession.GameSessionCreatedCommand;
import be.kdg.banditgames.gameplay.port.in.gameSession.GameSessionCreatedPort;
import be.kdg.banditgames.gameplay.port.out.gameSession.PersistGameSessionPort;
import org.springframework.stereotype.Service;

@Service
public class GameSessionCreatedImpl implements GameSessionCreatedPort {
    
    private final PersistGameSessionPort persistGameSessionPort;
    public GameSessionCreatedImpl(PersistGameSessionPort persistGameSessionPort) {
        this.persistGameSessionPort = persistGameSessionPort;
    }

    @Override
    public void project(GameSessionCreatedCommand gameSessionCreatedCommand) {
        GameSession gameSession = GameSession.createNew(
                GameId.of(gameSessionCreatedCommand.gameId()),
                SessionId.of(gameSessionCreatedCommand.sessionId()),
                gameSessionCreatedCommand.player1(),
                gameSessionCreatedCommand.player2()
        );
        
        persistGameSessionPort.save(gameSession);
    }
}

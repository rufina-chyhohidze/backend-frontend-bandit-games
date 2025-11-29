package be.kdg.banditgames.gameplay.core;

import be.kdg.banditgames.gameplay.domain.GameSession;
import be.kdg.banditgames.common.shared.GameId;
import be.kdg.banditgames.common.shared.SessionId;
import be.kdg.banditgames.gameplay.port.in.GameCreatedCommand;
import be.kdg.banditgames.gameplay.port.in.GameCreatedPort;
import be.kdg.banditgames.gameplay.port.out.gameSession.PersistGameSessionPort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GameCreatedImpl implements GameCreatedPort {
    
    private final PersistGameSessionPort persistGameSessionPort;
    
    public GameCreatedImpl(PersistGameSessionPort persistGameSessionPort) {
        this.persistGameSessionPort = persistGameSessionPort;
    }

    @Override
    public void project(GameCreatedCommand gameCreatedCommand) {
        GameSession gameSession = GameSession.createNew(
                GameId.of(UUID.randomUUID()), // temp measure, needs to be changed, here for ttesting rabbitmq
                SessionId.of(gameCreatedCommand.sessionId()),
                gameCreatedCommand.player1(),
                gameCreatedCommand.player2()
        );
        
        persistGameSessionPort.save(gameSession);
    }
}

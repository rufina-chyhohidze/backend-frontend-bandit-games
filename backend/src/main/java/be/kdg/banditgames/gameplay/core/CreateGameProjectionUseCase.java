package be.kdg.banditgames.gameplay.core;

import be.kdg.banditgames.gameplay.port.in.game.CreateGamePort;
import be.kdg.banditgames.gameplay.port.out.game.SaveGameProjectionPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Transactional
public class CreateGameProjectionUseCase implements CreateGamePort {
    private final SaveGameProjectionPort saveGameProjectionPort;

    public CreateGameProjectionUseCase(SaveGameProjectionPort saveGameProjectionPort) {
        this.saveGameProjectionPort = saveGameProjectionPort;
    }

    @Override
    public void createGame(String name, UUID gameId) {
        saveGameProjectionPort.saveGameProjection(gameId, name);
    }
}

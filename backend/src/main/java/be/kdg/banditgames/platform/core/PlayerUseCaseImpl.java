package be.kdg.banditgames.platform.core;

import be.kdg.banditgames.common.shared.PlayerId;
import be.kdg.banditgames.platform.domain.Player;
import be.kdg.banditgames.platform.port.in.player.CreatePlayerCommand;
import be.kdg.banditgames.platform.port.in.player.FindPlayerPort;
import be.kdg.banditgames.platform.port.in.player.PlayerCreationUseCase;
import be.kdg.banditgames.platform.port.out.player.LoadPlayerPort;
import be.kdg.banditgames.platform.port.out.player.SavePlayerPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class PlayerUseCaseImpl implements PlayerCreationUseCase, FindPlayerPort {

    private final SavePlayerPort savePlayerPort;
    private final LoadPlayerPort loadPlayerPort;

    public PlayerUseCaseImpl(SavePlayerPort savePlayerPort, LoadPlayerPort loadPlayerPort) {
        this.savePlayerPort = savePlayerPort;
        this.loadPlayerPort = loadPlayerPort;
    }

    @Override
    public Player createPlayer(CreatePlayerCommand command) {
        Optional<Player> existing = loadPlayerPort.loadById(PlayerId.of(command.playerId()));
        if (existing.isPresent()) return existing.get();

        Player player = Player.createNewWithId(PlayerId.of(command.playerId()), command.username());
        savePlayerPort.save(player);
        return player;
    }


    @Override
    public Optional<Player> findById(UUID playerId) {
        return loadPlayerPort.loadById(PlayerId.of(playerId));
    }

    @Override
    public List<Player> findByUsername(String usernamePart) {
        return loadPlayerPort.findByUsernameContainingIgnoreCase(usernamePart);
    }
}

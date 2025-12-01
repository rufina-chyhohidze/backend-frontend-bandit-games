package be.kdg.banditgames.platform.adapter.out.player;

import be.kdg.banditgames.common.shared.PlayerId;
import be.kdg.banditgames.platform.domain.Player;
import be.kdg.banditgames.platform.port.out.player.LoadPlayerPort;
import be.kdg.banditgames.platform.port.out.player.SavePlayerPort;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class PlayerJpaAdapter implements LoadPlayerPort, SavePlayerPort {
    private final PlayerJpaRepository repo;

    public PlayerJpaAdapter(PlayerJpaRepository repo) {
        this.repo = repo;
    }

    @Override
    public Optional<Player> loadById(PlayerId id) {
        UUID uuid = id.playerId();
        return repo.findById(uuid)
                .map(PlayerJpaMapper::toDomain);
    }

    @Override
    public void save(Player player) {
        var entity = PlayerJpaMapper.toEntity(player);
        repo.save(entity);
    }

}

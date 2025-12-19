package be.kdg.banditgames.platform.adapter.out.lobby;

import aj.org.objectweb.asm.commons.Remapper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface LobbyJpaRepository extends JpaRepository<LobbyJpaEntity, UUID> {

    boolean existsByHostPlayerIdOrGuestPlayerId(UUID hostPlayerId, UUID guestPlayerId);
    Optional<LobbyJpaEntity> findByHostPlayerIdOrGuestPlayerId(UUID uuid, UUID uuid1);
}

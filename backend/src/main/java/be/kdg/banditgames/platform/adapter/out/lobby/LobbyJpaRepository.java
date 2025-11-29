package be.kdg.banditgames.platform.adapter.out.lobby;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LobbyJpaRepository extends JpaRepository<LobbyJpaEntity, UUID> {

    boolean existsByHostPlayerIdOrGuestPlayerId(UUID hostPlayerId, UUID guestPlayerId);


}

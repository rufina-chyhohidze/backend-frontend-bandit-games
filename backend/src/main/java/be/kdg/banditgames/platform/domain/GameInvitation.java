package be.kdg.banditgames.platform.domain;

import be.kdg.banditgames.common.shared.PlayerId;
import be.kdg.banditgames.platform.domain.vo.InvitationId;
import be.kdg.banditgames.platform.domain.vo.LobbyId;

import java.time.LocalDateTime;

public class GameInvitation {
    private final InvitationId id;
    private final PlayerId fromPlayer;
    private final PlayerId toPlayer;
    private final LobbyId lobbyId;
    private InvitationStatus status;
    private final LocalDateTime createdAt;

    private GameInvitation(InvitationId id,
                           PlayerId fromPlayer,
                           PlayerId toPlayer,
                           LobbyId lobbyId,
                           InvitationStatus status,
                           LocalDateTime createdAt) {
        this.id = id;
        this.fromPlayer = fromPlayer;
        this.toPlayer = toPlayer;
        this.lobbyId = lobbyId;
        this.status = status;
        this.createdAt = createdAt;
    }

    public static GameInvitation createNew(PlayerId from, PlayerId to, LobbyId lobbyId) {
        return new GameInvitation(
                InvitationId.create(),
                from,
                to,
                lobbyId,
                InvitationStatus.PENDING,
                LocalDateTime.now()
        );
    }

    public static GameInvitation rehydrate(InvitationId id,
                                           PlayerId from,
                                           PlayerId to,
                                           LobbyId lobbyId,
                                           InvitationStatus status,
                                           LocalDateTime createdAt) {
        return new GameInvitation(id, from, to, lobbyId, status, createdAt);
    }

    public void accept(PlayerId accepter) {
        if (!toPlayer.equals(accepter)) throw new IllegalStateException("Only recipient can accept.");
        if (status != InvitationStatus.PENDING) throw new IllegalStateException("Invite is not pending.");
        status = InvitationStatus.ACCEPTED;
    }

    public void reject(PlayerId rejecter) {
        if (!toPlayer.equals(rejecter)) throw new IllegalStateException("Only recipient can reject.");
        if (status != InvitationStatus.PENDING) throw new IllegalStateException("Invite is not pending.");
        status = InvitationStatus.REJECTED;
    }

    public InvitationId getId() { return id; }
    public PlayerId getFromPlayer() { return fromPlayer; }
    public PlayerId getToPlayer() { return toPlayer; }
    public LobbyId getLobbyId() { return lobbyId; }
    public InvitationStatus getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }

}

package be.kdg.banditgames.platform.core.invitation;

import be.kdg.banditgames.common.shared.PlayerId;
import be.kdg.banditgames.common.shared.PlayerType;
import be.kdg.banditgames.platform.domain.Friendship;
import be.kdg.banditgames.platform.domain.FriendshipStatus;
import be.kdg.banditgames.platform.domain.GameInvitation;
import be.kdg.banditgames.platform.domain.Lobby;
import be.kdg.banditgames.platform.domain.vo.InvitationId;
import be.kdg.banditgames.platform.domain.vo.LobbyId;
import be.kdg.banditgames.platform.port.in.friendship.FindFriendshipPort;
import be.kdg.banditgames.platform.port.in.invitation.AcceptGameInvitationCommand;
import be.kdg.banditgames.platform.port.in.invitation.GameInvitationUseCase;
import be.kdg.banditgames.platform.port.in.invitation.InviteFriendToLobbyCommand;
import be.kdg.banditgames.platform.port.in.invitation.RejectGameInvitationCommand;
import be.kdg.banditgames.platform.port.out.invitation.LoadGameInvitationPort;
import be.kdg.banditgames.platform.port.out.invitation.PersistGameInvitationPort;
import be.kdg.banditgames.platform.port.out.lobby.LoadLobbyPort;
import be.kdg.banditgames.platform.port.out.lobby.LobbyLookupPort;
import be.kdg.banditgames.platform.port.out.lobby.PersistLobbyPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class GameInvitationUseCaseImpl implements GameInvitationUseCase {
    private final FindFriendshipPort friendshipPort;
    private final LoadGameInvitationPort loadInvitationPort;
    private final PersistGameInvitationPort persistInvitationPort;
    private final LoadLobbyPort loadLobbyPort;
    private final PersistLobbyPort persistLobbyPort;
    private final LobbyLookupPort lobbyLookupPort;

    public GameInvitationUseCaseImpl(
            FindFriendshipPort friendshipPort,
            LoadGameInvitationPort loadInvitationPort,
            PersistGameInvitationPort persistInvitationPort,
            LoadLobbyPort loadLobbyPort,
            PersistLobbyPort persistLobbyPort,
            LobbyLookupPort lobbyLookupPort
    ) {
        this.friendshipPort = friendshipPort;
        this.loadInvitationPort = loadInvitationPort;
        this.persistInvitationPort = persistInvitationPort;
        this.loadLobbyPort = loadLobbyPort;
        this.persistLobbyPort = persistLobbyPort;
        this.lobbyLookupPort = lobbyLookupPort;
    }

    @Override
    public GameInvitation inviteFriendToLobby(InviteFriendToLobbyCommand command) {
        PlayerId from = command.fromPlayer();
        PlayerId to = command.toPlayer();

        Friendship friendship = friendshipPort.getFriendshipBetween(from, to)
                .orElseThrow(() -> new IllegalStateException("Friendship does not exist."));

        if (friendship.getStatus() != FriendshipStatus.ACCEPTED) {
            throw new IllegalStateException("Friendship is not accepted.");
        }

        loadInvitationPort.findPendingBetween(from, to).ifPresent(i -> {
            throw new IllegalStateException("A pending invitation already exists.");
        });

        if (lobbyLookupPort.isPlayerInAnyLobby(from)) {
            throw new IllegalStateException("You are already in a lobby.");
        }

        Lobby lobby = Lobby.createNew(from);
        persistLobbyPort.saveLobby(lobby);

        GameInvitation invitation = GameInvitation.createNew(from, to, lobby.getLobbyId());
        persistInvitationPort.save(invitation);

        return invitation;
    }

    @Override
    public LobbyId acceptInvite(AcceptGameInvitationCommand command) {
        GameInvitation invitation = loadInvitationPort.loadById(
                InvitationId.of(command.invitationId())
        ).orElseThrow(() -> new IllegalStateException("Invitation not found."));

        invitation.accept(command.accepter());

        if (lobbyLookupPort.isPlayerInAnyLobby(command.accepter())) {
            throw new IllegalStateException("You are already in a lobby.");
        }

        Lobby lobby = loadLobbyPort.loadLobbyById(invitation.getLobbyId())
                .orElseThrow(() -> new IllegalStateException("Lobby not found."));

        if (lobby.getGuestPlayer() != null) {
            throw new IllegalStateException("Lobby already has a guest.");
        }

        lobby.changeGuest(command.accepter(), PlayerType.HUMAN);
        persistLobbyPort.saveLobby(lobby);

        persistInvitationPort.save(invitation);

        return lobby.getLobbyId();
    }

    @Override
    public void rejectInvite(RejectGameInvitationCommand command) {
        GameInvitation invitation = loadInvitationPort.loadById(
                InvitationId.of(command.invitationId())
        ).orElseThrow(() -> new IllegalStateException("Invitation not found."));

        invitation.reject(command.rejecter());
        persistInvitationPort.save(invitation);
    }

    @Override
    public List<GameInvitation> getPendingInvites(PlayerId playerId) {
        return loadInvitationPort.loadPendingForPlayer(playerId);
    }
}

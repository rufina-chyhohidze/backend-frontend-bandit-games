package be.kdg.banditgames.platform.port.in.invitation;

import be.kdg.banditgames.common.shared.PlayerId;
import be.kdg.banditgames.platform.domain.GameInvitation;
import be.kdg.banditgames.platform.domain.vo.LobbyId;

import java.util.List;
import java.util.UUID;

public interface GameInvitationUseCase {
    GameInvitation inviteFriendToLobby(InviteFriendToLobbyCommand command);
    LobbyId acceptInvite(AcceptGameInvitationCommand command);
    void rejectInvite(RejectGameInvitationCommand command);
    List<GameInvitation> getPendingInvites(PlayerId playerId);
}

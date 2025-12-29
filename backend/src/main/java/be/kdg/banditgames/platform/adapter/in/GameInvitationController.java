package be.kdg.banditgames.platform.adapter.in;

import be.kdg.banditgames.common.shared.PlayerId;
import be.kdg.banditgames.platform.adapter.in.requests.invitation.AcceptInviteRequest;
import be.kdg.banditgames.platform.adapter.in.requests.invitation.InviteToGameRequest;
import be.kdg.banditgames.platform.adapter.in.requests.invitation.LobbyRedirectResponse;
import be.kdg.banditgames.platform.domain.vo.LobbyId;
import be.kdg.banditgames.platform.port.in.invitation.AcceptGameInvitationCommand;
import be.kdg.banditgames.platform.port.in.invitation.GameInvitationUseCase;
import be.kdg.banditgames.platform.port.in.invitation.InviteFriendToLobbyCommand;
import be.kdg.banditgames.platform.port.in.invitation.RejectGameInvitationCommand;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/game-invitations")
public class GameInvitationController {
    private final GameInvitationUseCase useCase;

    public GameInvitationController(GameInvitationUseCase useCase) {
        this.useCase = useCase;
    }
    @PostMapping("/invite")
    public ResponseEntity<UUID> invite(
            @AuthenticationPrincipal Jwt principal,
            @RequestBody InviteToGameRequest req
    ) {
        PlayerId from = new PlayerId(UUID.fromString(principal.getSubject()));

        var invitation = useCase.inviteFriendToLobby(
                new InviteFriendToLobbyCommand(from, new PlayerId(req.toPlayerId()))
        );

        return ResponseEntity.ok(invitation.getId().id());
    }

    @PostMapping("/accept")
    public ResponseEntity<LobbyRedirectResponse> accept(
            @AuthenticationPrincipal Jwt principal,
            @RequestBody AcceptInviteRequest req
    ) {
        PlayerId me = new PlayerId(UUID.fromString(principal.getSubject()));

        LobbyId lobbyId = useCase.acceptInvite(
                new AcceptGameInvitationCommand(req.invitationId(), me)
        );

        return ResponseEntity.ok(new LobbyRedirectResponse(lobbyId.lobbyID()));
    }

    @PostMapping("/reject")
    public ResponseEntity<Void> reject(
            @AuthenticationPrincipal Jwt principal,
            @RequestBody AcceptInviteRequest req
    ) {
        PlayerId me = new PlayerId(UUID.fromString(principal.getSubject()));

        useCase.rejectInvite(
                new RejectGameInvitationCommand(req.invitationId(), me)
        );

        return ResponseEntity.ok().build();
    }
    @GetMapping("/pending")
    public ResponseEntity<?> pending(@AuthenticationPrincipal Jwt principal) {
        PlayerId me = new PlayerId(UUID.fromString(principal.getSubject()));
        return ResponseEntity.ok(useCase.getPendingInvites(me));
    }


}

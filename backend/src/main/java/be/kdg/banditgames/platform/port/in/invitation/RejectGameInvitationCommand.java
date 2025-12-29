package be.kdg.banditgames.platform.port.in.invitation;

import be.kdg.banditgames.common.shared.PlayerId;

import java.util.UUID;

public record RejectGameInvitationCommand(UUID invitationId,
                                          PlayerId rejecter) {
}

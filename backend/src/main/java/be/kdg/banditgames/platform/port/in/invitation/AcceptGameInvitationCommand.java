package be.kdg.banditgames.platform.port.in.invitation;

import be.kdg.banditgames.common.shared.PlayerId;

import java.util.UUID;

public record AcceptGameInvitationCommand(UUID invitationId,
                                          PlayerId accepter) {
}

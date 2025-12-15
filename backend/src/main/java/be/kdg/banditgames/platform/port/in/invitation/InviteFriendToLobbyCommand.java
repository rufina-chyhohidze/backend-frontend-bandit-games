package be.kdg.banditgames.platform.port.in.invitation;

import be.kdg.banditgames.common.shared.PlayerId;

public record InviteFriendToLobbyCommand(PlayerId fromPlayer, PlayerId toPlayer) {
}

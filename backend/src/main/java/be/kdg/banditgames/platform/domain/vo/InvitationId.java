package be.kdg.banditgames.platform.domain.vo;

import java.util.UUID;

public record InvitationId(UUID id) {
    public static InvitationId create() { return new InvitationId(UUID.randomUUID()); }
    public static InvitationId of(UUID id) { return new InvitationId(id); }
}

package be.kdg.banditgames.platform.domain.vo;

import java.util.UUID;

public record AdminId(UUID adminId) {
    public AdminId {
        if (adminId == null) {
            throw new IllegalArgumentException("AdminId cannot be null");
        }
    }

    public static AdminId newId() {
        return new AdminId(UUID.randomUUID());
    }

    @Override
    public String toString() {
        return adminId.toString();
    }
}

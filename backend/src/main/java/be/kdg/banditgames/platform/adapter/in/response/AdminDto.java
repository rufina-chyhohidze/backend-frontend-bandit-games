package be.kdg.banditgames.platform.adapter.in.dto;

import be.kdg.banditgames.platform.domain.Admin;

import java.util.UUID;

public record AdminDto(UUID id) {
    public static AdminDto fromDomain(Admin admin) {
        return new AdminDto(admin.getAdminId().adminId());
    }
}

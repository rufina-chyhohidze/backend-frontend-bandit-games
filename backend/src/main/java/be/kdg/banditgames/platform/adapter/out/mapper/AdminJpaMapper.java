package be.kdg.banditgames.platform.adapter.out.mapper;

import be.kdg.banditgames.platform.adapter.out.admin.AdminJpaEntity;
import be.kdg.banditgames.platform.domain.Admin;
import be.kdg.banditgames.platform.domain.vo.AdminId;

import java.util.UUID;

public final class AdminJpaMapper {
    private AdminJpaMapper() {
    }

    public static Admin toDomain(AdminJpaEntity entity) {
        if (entity == null) return null;
        AdminId adminId = new AdminId(entity.getId());
        return new Admin(adminId);
    }

    public static AdminJpaEntity toEntity(Admin admin) {
        if (admin == null) return null;
        UUID id = admin.getAdminId().adminId();
        return new AdminJpaEntity(id);
    }
}

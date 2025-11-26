package be.kdg.banditgames.platform.domain;

import be.kdg.banditgames.platform.domain.vo.AdminId;

public class Admin {
    private final AdminId adminId;

    public Admin(AdminId adminId) {
        this.adminId = adminId;
    }

    public static Admin createNew() {
        return new Admin(AdminId.newId());
    }

    public AdminId getAdminId() {
        return adminId;
    }
}

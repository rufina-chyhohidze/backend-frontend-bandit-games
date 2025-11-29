package be.kdg.banditgames.platform.port.out;

import be.kdg.banditgames.platform.domain.Admin;
import be.kdg.banditgames.platform.domain.vo.AdminId;

import java.util.Optional;

public interface LoadAdminPort {
    Optional<Admin> loadById(AdminId id);
}

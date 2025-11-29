package be.kdg.banditgames.platform.adapter.out.admin;

import be.kdg.banditgames.platform.adapter.out.mapper.AdminJpaMapper;
import be.kdg.banditgames.platform.domain.Admin;
import be.kdg.banditgames.platform.domain.vo.AdminId;
import be.kdg.banditgames.platform.port.out.LoadAdminPort;
import be.kdg.banditgames.platform.port.out.SaveAdminPort;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class AdminJpaAdapter implements LoadAdminPort, SaveAdminPort {
    private final AdminJpaRepository adminJpaRepository;


  public AdminJpaAdapter(AdminJpaRepository adminJpaRepository) {
      this.adminJpaRepository = adminJpaRepository;
  }

    @Override
    public Optional<Admin> loadById(AdminId id) {
        UUID uuid = id.adminId();
        return adminJpaRepository.findById(uuid)
                .map(AdminJpaMapper::toDomain);
    }

    @Override
    public void save(Admin admin) {
        AdminJpaEntity entity = AdminJpaMapper.toEntity(admin);
        adminJpaRepository.save(entity);
    }
}

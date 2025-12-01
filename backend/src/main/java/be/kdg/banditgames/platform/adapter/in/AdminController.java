package be.kdg.banditgames.platform.adapter.in;

import be.kdg.banditgames.platform.adapter.in.response.AdminDto;
import be.kdg.banditgames.platform.domain.Admin;
import be.kdg.banditgames.platform.domain.vo.AdminId;
import be.kdg.banditgames.platform.port.out.LoadAdminPort;
import be.kdg.banditgames.platform.port.out.SaveAdminPort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final LoadAdminPort loadAdminPort;
    private final SaveAdminPort saveAdminPort;

    public AdminController(LoadAdminPort loadAdminPort, SaveAdminPort saveAdminPort) {
        this.loadAdminPort = loadAdminPort;
        this.saveAdminPort = saveAdminPort;
    }

    /**
     * First call for a Keycloak user with role "admin".
     * Creates Admin row if doesn't exist, otherwise returns existing one.
     */
    @PostMapping("/register")
    @PreAuthorize("hasAuthority('admin')")
    public AdminDto register(@AuthenticationPrincipal Jwt jwt) {
        UUID keycloakId = UUID.fromString(jwt.getSubject());
        AdminId adminId = new AdminId(keycloakId);

        Admin admin = loadAdminPort.loadById(adminId)
                .orElseGet(() -> {
                    Admin newAdmin = new Admin(adminId);
                    saveAdminPort.save(newAdmin);
                    return newAdmin;
                });

        return AdminDto.fromDomain(admin);
    }

    @GetMapping("/me")
    @PreAuthorize("hasAuthority('admin')")
    public AdminDto me(@AuthenticationPrincipal Jwt jwt) {
        UUID keycloakId = UUID.fromString(jwt.getSubject());
        AdminId adminId = new AdminId(keycloakId);

        Admin admin = loadAdminPort.loadById(adminId)
                .orElseGet(() -> {
                    Admin newAdmin = new Admin(adminId);
                    saveAdminPort.save(newAdmin);
                    return newAdmin;
                });

        return AdminDto.fromDomain(admin);
    }
}

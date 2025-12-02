package be.kdg.banditgames.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class KeycloakRealmRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    private static final String CLIENT_NAME = "banditgames-frontend";

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        List<GrantedAuthority> authorities = new ArrayList<>();

        Map<String, Object> realmAccess = jwt.getClaim("realm_access");
        if (realmAccess != null) {
            Object roles = realmAccess.get("roles");
            if (roles instanceof List<?> roleList) {
                authorities.addAll(
                        roleList.stream()
                                .filter(String.class::isInstance)
                                .map(String.class::cast)
                                .map(SimpleGrantedAuthority::new)
                                .toList()
                );
            }
        }

        Map<String, Object> resourceAccess = jwt.getClaim("resource_access");
        if (resourceAccess != null) {
            Object client = resourceAccess.get(CLIENT_NAME);
            if (client instanceof Map<?, ?> clientMap) {
                Object clientRoles = clientMap.get("roles");
                if (clientRoles instanceof List<?> roleList) {
                    authorities.addAll(
                            roleList.stream()
                                    .filter(String.class::isInstance)
                                    .map(String.class::cast)
                                    .map(SimpleGrantedAuthority::new)
                                    .toList()
                    );
                }
            }
        }

        return authorities;
    }
}

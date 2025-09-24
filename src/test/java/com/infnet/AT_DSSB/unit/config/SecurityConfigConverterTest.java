package com.infnet.AT_DSSB.unit.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SecurityConfigConverterTest {

    @Autowired
    private JwtAuthenticationConverter converter;

    private static Jwt jwtWithClaims(Map<String, Object> claims) {
        return Jwt.withTokenValue("t")
                .header("alg", "none")
                .claim("sub", "test-user")
                .claims(c -> {
                    if (claims != null) c.putAll(claims);
                })
                .build();
    }

    @Test
    void deveExtrairRoleProfessor_deRealmAccess() {
        Jwt jwt = jwtWithClaims(Map.of("realm_access", Map.of("roles", List.of("PROFESSOR", "USER"))));

        Authentication auth = converter.convert(jwt);
        List<String> authorities = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        assertThat(authorities).contains("ROLE_PROFESSOR", "ROLE_USER");
    }

    @Test
    void deveExtrairRoleProfessor_deResourceAccess() {
        Jwt jwt = jwtWithClaims(Map.of(
                "resource_access", Map.of(
                        "at-dssb-api", Map.of("roles", List.of("PROFESSOR"))
                )
        ));

        Authentication auth = converter.convert(jwt);
        List<String> authorities = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        assertThat(authorities).contains("ROLE_PROFESSOR");
    }

    @Test
    void semClaimsNaoGeraAuthorities() {
        Jwt jwt = jwtWithClaims(Map.of());
        var auth = converter.convert(jwt);
        assertThat(auth.getAuthorities()).isEmpty();
    }

}
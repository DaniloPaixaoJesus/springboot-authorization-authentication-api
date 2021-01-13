package br.com.danilopaixao.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.sql.Date;
import java.util.stream.Collectors;

public class JwtUtils {

    private Authentication auth;
    private Long now;
    private final SecurityConfig securityConfig;

    public JwtUtils(Authentication auth, Long now, SecurityConfig securityConfig) {
        this.auth = auth;
        this.now = now;
        this.securityConfig = securityConfig;
    }

    public String getToken() {
        return Jwts.builder()
            .setSubject(auth.getName())
            // Convert to list of strings.
            // This is important because it affects the way we get them back in the Gateway.
            .claim("authorities", auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
            .setIssuedAt(new Date(now))
            .setExpiration(new Date(now + securityConfig.getExpiration() * 1000))  // in milliseconds
            .signWith(SignatureAlgorithm.HS512, securityConfig.getSecret().getBytes())
            .compact();
    }

}


package br.com.danilopaixao.ws.core.security;

import org.springframework.beans.factory.annotation.Value;

import lombok.Data;

@Data
public class SecurityConfig {
    @Value("${security.uri:/auth/**}")
    private String Uri;

    @Value("${security.header:Authorization}")
    private String header;

    @Value("${security.prefix:Bearer }")
    private String prefix;

    @Value("${security.expiration:#{24*60*60}}")
    private int expiration;

    @Value("${security.secret:JwtSecretKey}")
    private String secret;
    
    @Value("${security.swagger:/swagger-ui**}")
    private String swaggerUI;

}

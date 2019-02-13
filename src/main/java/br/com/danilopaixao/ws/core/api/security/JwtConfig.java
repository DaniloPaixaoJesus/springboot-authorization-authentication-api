package br.com.danilopaixao.ws.core.api.security;

import org.springframework.beans.factory.annotation.Value;

import lombok.Data;

@Data
public class JwtConfig {
    @Value("${security.jwt.uri:/auth/**}")
    private String Uri;

    @Value("${security.jwt.header:Authorization}")
    private String header;

    @Value("${security.jwt.prefix:Bearer }")
    private String prefix;

    @Value("${security.jwt.expiration:#{24*60*60}}")
    private int expiration;

    @Value("${security.jwt.secret:JwtSecretKey}")
    private String secret;
    
    @Value("${security.jwt.swagger:/swagger-ui**}")
    private String swaggerUI;

}

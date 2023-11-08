package com.rit.starterboot.configuration.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.rit.starterboot.configuration.security.jwt.template.JwtTemplate;

import java.time.Instant;

public class Auth0JwtEncoder implements JwtEncoder {

    private final Algorithm algorithm;

    public Auth0JwtEncoder(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    @Override
    public String encode(JwtTemplate template) {
        return JWT.create()
                  .withExpiresAt(Instant.MAX)
                  .withSubject(template.getSubject())
                  .sign(algorithm);
    }
}

package com.rit.starterboot.servlet.configuration.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.rit.starterboot.servlet.configuration.security.jwt.template.JwtTemplate;

class Auth0JwtEncoder implements JwtEncoder {

    private final Algorithm algorithm;

    Auth0JwtEncoder(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    @Override
    public String encode(JwtTemplate template) {
        return JWT.create()
                  .withExpiresAt(template.getExpiresAt())
                  .withSubject(template.getSubject())
                  .sign(algorithm);
    }
}

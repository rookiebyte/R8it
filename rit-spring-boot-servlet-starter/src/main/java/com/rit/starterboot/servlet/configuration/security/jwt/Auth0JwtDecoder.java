package com.rit.starterboot.servlet.configuration.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

class Auth0JwtDecoder implements JwtDecoder {

    private final Algorithm algorithm;

    Auth0JwtDecoder(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    @Override
    public Jwt decode(String token) {
        var jwt = JWT.require(algorithm).build().verify(token);
        return new Auth0Jwt(jwt);
    }
}

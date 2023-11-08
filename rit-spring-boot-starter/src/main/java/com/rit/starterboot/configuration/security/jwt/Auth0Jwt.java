package com.rit.starterboot.configuration.security.jwt;


import com.auth0.jwt.interfaces.DecodedJWT;

class Auth0Jwt implements Jwt {

    private final DecodedJWT jwt;

    Auth0Jwt(DecodedJWT jwt) {
        this.jwt = jwt;
    }

    @Override
    public String getTokenValue() {
        return jwt.getToken();
    }

    @Override
    public String getSubject() {
        return jwt.getSubject();
    }
}

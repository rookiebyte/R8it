package com.rit.starterboot.servlet.configuration.security.jwt;

import com.rit.robusta.util.Preconditions;
import org.springframework.security.authentication.AbstractAuthenticationToken;

import java.util.Collections;
import java.util.Objects;

class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private final Jwt jwt;

    JwtAuthenticationToken(Jwt jwt) {
        super(Collections.emptyList());
        Preconditions.checkArgument(jwt, Objects::nonNull, "Jwt cannot be null");
        this.jwt = jwt;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return jwt.getTokenValue();
    }

    @Override
    public Object getPrincipal() {
        return jwt;
    }
}

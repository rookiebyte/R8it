package com.rit.user.configuration.jwt;

import com.rit.starterboot.servlet.configuration.jwt.properties.JwtProperties;
import com.rit.starterboot.servlet.configuration.security.jwt.template.JwtTemplate;
import com.rit.user.domain.user.User;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class UserJwtTemplate extends JwtTemplate {

    private final User user;
    private final JwtProperties jwtProperties;

    public UserJwtTemplate(User user, JwtProperties jwtProperties) {
        super();
        this.user = user;
        this.jwtProperties = jwtProperties;
    }

    @Override
    public String getSubject() {
        return user.getId().toString();
    }

    @Override
    public Instant getExpiresAt() {
        return Instant.now().plus(jwtProperties.encoder().validPeriodInHours(), ChronoUnit.HOURS);
    }
}

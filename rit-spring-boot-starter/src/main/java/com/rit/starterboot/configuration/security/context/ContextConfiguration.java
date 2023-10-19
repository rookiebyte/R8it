package com.rit.starterboot.configuration.security.context;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.context.annotation.RequestScope;

@Configuration
public class ContextConfiguration {

    private final JwtConverter jwtConverter;

    ContextConfiguration() {
        jwtConverter = new JwtConverter();
    }

    @Bean
    @RequestScope
    public UserContextProvider userContextProvider() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        var jwt = (Jwt) auth.getPrincipal();
        return new UserContextProvider(jwtConverter.toContext(jwt));
    }
}

package com.rit.starterboot.servlet.configuration.security.context;

import com.rit.starterboot.servlet.configuration.security.jwt.Jwt;
import com.rit.starterboot.servlet.domain.user.UserContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.annotation.RequestScope;

@Configuration
public class ContextConfiguration {

    @Bean
    @RequestScope
    public UserContextProvider userContextProvider() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        var jwt = (Jwt) auth.getPrincipal();
        var context = new UserContext(jwt.getTokenValue(), jwt.getSubject());
        return new UserContextProvider(context);
    }
}

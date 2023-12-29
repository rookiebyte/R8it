package com.rit.user.configuration.jwt;

import com.rit.starterboot.servlet.configuration.jwt.properties.JwtProperties;
import com.rit.starterboot.servlet.configuration.security.jwt.JwtEncoder;
import com.rit.user.domain.user.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class JwtFacade {

    private final JwtEncoder jwtEncoder;
    private final JwtProperties jwtProperties;

    public String createJwt(User user) {
        return jwtEncoder.encode(new UserJwtTemplate(user, jwtProperties));
    }
}

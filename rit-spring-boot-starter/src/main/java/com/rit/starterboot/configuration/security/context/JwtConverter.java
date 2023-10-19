package com.rit.starterboot.configuration.security.context;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.rit.starterboot.domain.user.User;
import com.rit.starterboot.domain.user.UserContext;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Date;
import java.util.function.Supplier;

public final class JwtConverter {

    public String fromContext(User user, Algorithm algorithm,
                              Supplier<Date> expDateProvider) {
        return JWT.create()
                  .withSubject(user.getId())
                  .withNotBefore(new Date())
                  .withExpiresAt(expDateProvider.get())
                  .sign(algorithm);
    }

    public UserContext toContext(Jwt jwt) {
        return new UserContext(
                jwt.getTokenValue(),
                jwt.getSubject()
        );
    }
}

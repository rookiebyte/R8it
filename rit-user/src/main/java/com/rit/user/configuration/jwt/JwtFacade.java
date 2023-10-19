package com.rit.user.configuration.jwt;

import com.auth0.jwt.algorithms.Algorithm;
import com.rit.starterboot.configuration.jwt.JwtKeyStore;
import com.rit.starterboot.configuration.jwt.properties.JwtProperties;
import com.rit.starterboot.configuration.security.context.JwtConverter;
import com.rit.starterboot.domain.user.User;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Calendar;
import java.util.Date;

@Component
public class JwtFacade {

    private final JwtKeyStore jwtKeyStore;
    private final JwtProperties jwtProperties;
    private final JwtConverter jwtConverter;

    public JwtFacade(JwtKeyStore jwtKeyStore, JwtProperties jwtProperties) {
        this.jwtKeyStore = jwtKeyStore;
        this.jwtProperties = jwtProperties;
        this.jwtConverter = new JwtConverter();
    }

    private Algorithm getSignAlgorithm() {
        return Algorithm.RSA256(jwtKeyStore.getPublicKey(), jwtKeyStore.getPrivateKey());
    }

    @SuppressWarnings("PMD.UnusedFormalParameter")
    private Date getExpiresAt() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Instant.now().toEpochMilli());
        calendar.add(Calendar.HOUR, jwtProperties.validPeriodInHours());
        return calendar.getTime();
    }

    public String createJwt(User user) {
        return jwtConverter.fromContext(user, getSignAlgorithm(), this::getExpiresAt);
    }
}

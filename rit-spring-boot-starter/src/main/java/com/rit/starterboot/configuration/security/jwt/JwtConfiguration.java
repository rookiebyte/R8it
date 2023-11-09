package com.rit.starterboot.configuration.security.jwt;

import com.auth0.jwt.algorithms.Algorithm;
import com.rit.starterboot.configuration.jwt.JwtKeyStore;
import com.rit.starterboot.configuration.jwt.properties.JwtProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@EnableConfigurationProperties({JwtProperties.class})
public class JwtConfiguration {

    private final JwtKeyStore jwtKeyStore;

    public JwtConfiguration(JwtProperties jwtProperties) {
        this.jwtKeyStore = new JwtKeyStore(jwtProperties);
    }

    @Bean
    @ConditionalOnProperty(prefix = "app.jwt.encoder", name = "enabled", havingValue = "true")
    public JwtEncoder jwtEncoder() {
        return new Auth0JwtEncoder(Algorithm.RSA256(jwtKeyStore.getPublicKey(), jwtKeyStore.getPrivateKey()));
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return new Auth0JwtDecoder(Algorithm.RSA256(jwtKeyStore.getPublicKey()));
    }
}

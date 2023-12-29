package com.rit.starterboot.servlet.configuration.security.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Optional;

@ConfigurationProperties(prefix = "app.cors")
public record CorsProperties(

        List<String> allowedOrigins
) {

    public CorsProperties {
        allowedOrigins = Optional.ofNullable(allowedOrigins).orElse(List.of());
    }
}

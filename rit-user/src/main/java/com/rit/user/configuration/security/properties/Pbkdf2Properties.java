package com.rit.user.configuration.security.properties;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "app.pbkdf2")
@Validated
public record Pbkdf2Properties(

        @NotEmpty
        String pbkdf2Secret,
        @NotNull
        Integer saltLength,
        @NotNull
        Integer iteration
) {
}

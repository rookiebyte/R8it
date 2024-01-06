package com.rit.user.configuration.otp.properties;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "app.otp")
@Validated
public record OtpProperties(

        @NotEmpty
        String secret,

        @NotNull
        Integer defaultLength,

        @NotNull
        Long defaultExpDelay
) {
}

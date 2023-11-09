package com.rit.starterboot.configuration.jwt.properties;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "app.jwt")
@Validated
public record JwtProperties(

        @NotEmpty
        String keyStorePath,
        @NotEmpty
        String keyStorePassword,
        @NotEmpty
        String keyAlias,
        @NotEmpty
        String privateKeyPassphrase,
        EncoderProperties encoder

) {

    public char[] keyStorePasswordAsArray() {
        return keyStorePassword.toCharArray();
    }

    public char[] privateKeyPassphraseAsArray() {
        return privateKeyPassphrase.toCharArray();
    }

    public record EncoderProperties(

            boolean enabled,

            @NotNull
            Integer validPeriodInHours
    ) {
    }
}

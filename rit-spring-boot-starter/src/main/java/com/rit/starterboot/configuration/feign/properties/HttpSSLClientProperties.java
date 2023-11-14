package com.rit.starterboot.configuration.feign.properties;

import jakarta.validation.constraints.NotEmpty;

public record HttpSSLClientProperties(

        @NotEmpty
        String keyPassword,
        @NotEmpty
        String keyStore,
        @NotEmpty
        String keyStorePassword,
        @NotEmpty
        String trustStore,
        @NotEmpty
        String trustStorePassword
) {

    public HttpSSLClientProperties {
    }

    public char[] keyPasswordAsArray() {
        return keyPassword.toCharArray();
    }

    public char[] keyStorePasswordAsArray() {
        return keyStorePassword.toCharArray();
    }

    public char[] trustStorePasswordAsArray() {
        return trustStorePassword.toCharArray();
    }
}

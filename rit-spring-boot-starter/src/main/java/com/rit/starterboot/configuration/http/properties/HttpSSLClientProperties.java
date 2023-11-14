package com.rit.starterboot.configuration.http.properties;

public record HttpSSLClientProperties(

        String keyAlias,
        String keyPassword,
        String keyStore,
        String keyStorePassword,
        String trustStore,
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

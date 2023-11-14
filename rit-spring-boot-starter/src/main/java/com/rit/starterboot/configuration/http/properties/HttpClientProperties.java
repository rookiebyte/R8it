package com.rit.starterboot.configuration.http.properties;

import org.springframework.validation.annotation.Validated;

@Validated
public record HttpClientProperties(

        String url,
        ConnectionType type,
        String name,
        boolean enabled,
        HttpSSLClientProperties ssl
) {
}

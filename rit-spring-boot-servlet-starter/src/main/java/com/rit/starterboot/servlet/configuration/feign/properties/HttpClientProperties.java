package com.rit.starterboot.servlet.configuration.feign.properties;

import org.springframework.validation.annotation.Validated;

@Validated
public record HttpClientProperties(

        String url,
        ConnectionType type,
        String name,
        HttpSSLClientProperties ssl
) {
}

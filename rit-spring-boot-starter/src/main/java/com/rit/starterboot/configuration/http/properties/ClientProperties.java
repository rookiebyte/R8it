package com.rit.starterboot.configuration.http.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "http.client")
@Validated
public record ClientProperties(

        HttpClientProperties notification
) {
}

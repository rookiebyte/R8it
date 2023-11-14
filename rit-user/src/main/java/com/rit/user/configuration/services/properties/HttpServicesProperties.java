package com.rit.user.configuration.services.properties;

import com.rit.starterboot.configuration.feign.properties.HttpClientProperties;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "http.client")
@Validated
public record HttpServicesProperties(

        @NotNull
        HttpClientProperties notification
) {
}

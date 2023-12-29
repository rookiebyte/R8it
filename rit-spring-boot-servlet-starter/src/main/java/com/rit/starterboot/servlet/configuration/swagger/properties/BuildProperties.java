package com.rit.starterboot.servlet.configuration.swagger.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("build")
public record BuildProperties(

        String version,
        String description
) {
}

package com.rit.user.configuration.services;

import com.rit.starterboot.configuration.feign.FeignClientFactory;
import com.rit.starterboot.domain.notification.NotificationService;
import com.rit.starterboot.infrastructure.notification.NotificationServiceConfiguration;
import com.rit.user.configuration.services.properties.HttpServicesProperties;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@AllArgsConstructor
@Configuration
@EnableConfigurationProperties({HttpServicesProperties.class})
public class ServicesConfiguration {

    private final HttpServicesProperties httpServicesProperties;

    @Bean
    NotificationService notificationService(FeignClientFactory feignClientFactory) {
        return new NotificationServiceConfiguration(feignClientFactory, httpServicesProperties.notification()).notificationService();
    }
}

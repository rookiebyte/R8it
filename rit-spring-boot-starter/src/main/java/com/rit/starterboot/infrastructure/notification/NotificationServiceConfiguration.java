package com.rit.starterboot.infrastructure.notification;

import com.rit.starterboot.configuration.feign.FeignClientFactory;
import com.rit.starterboot.configuration.feign.properties.HttpClientProperties;
import com.rit.starterboot.domain.notification.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;

@AllArgsConstructor
public class NotificationServiceConfiguration {

    private final FeignClientFactory feignClientFactory;
    private final HttpClientProperties notificationClientProperties;

    @Bean
    public NotificationService notificationService() {
        return new NotificationFacadeService(notificationClient(notificationClientProperties));
    }

    private NotificationClient notificationClient(HttpClientProperties notificationClientProperties) {
        return feignClientFactory.create(NotificationClient.class, notificationClientProperties);
    }
}

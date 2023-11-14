package com.rit.starterboot.configuration.http;

import com.rit.starterboot.configuration.http.properties.ClientProperties;
import com.rit.starterboot.infrastructure.notification.NotificationClient;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@AllArgsConstructor
@Configuration
@EnableConfigurationProperties({ClientProperties.class})
public class FeignClientConfiguration {

    private final FeignClientFactory feignClientFactory;
    private final ClientProperties clientProperties;

    @Bean
    @ConditionalOnProperty(prefix = "http.client.notification", name = "enabled", havingValue = "true")
    public NotificationClient notificationClient() {
        return feignClientFactory.create(NotificationClient.class, clientProperties.notification());
    }
}

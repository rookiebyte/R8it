package com.rit.starterboot.configuration.http;

import com.rit.starterboot.configuration.http.properties.ClientsProperties;
import com.rit.starterboot.domain.notification.NotificationService;
import com.rit.starterboot.infrastructure.notification.NotificationFacadeService;
import com.rit.starterboot.infrastructure.notification.NotificationClient;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@AllArgsConstructor
@Configuration
@EnableConfigurationProperties({ClientsProperties.class})
public class HttpClientConfiguration {

    private final ClientsProperties clientsProperties;

    @Bean
    public FeignClientFactory feignClientFactory(ApplicationContext applicationContext, LoadBalancerClient loadBalancerClient,
                                                 LoadBalancerClientFactory loadBalancerClientFactory) {
        return new FeignClientFactory(applicationContext, loadBalancerClient, loadBalancerClientFactory);
    }

    @Bean
    @ConditionalOnProperty(prefix = "http.client.notification", name = "enabled", havingValue = "true")
    public NotificationService mailNotificationService(FeignClientFactory feignClientFactory) {
        return new NotificationFacadeService(notificationClient(feignClientFactory));
    }

    private NotificationClient notificationClient(FeignClientFactory feignClientFactory) {
        return feignClientFactory.create(NotificationClient.class, clientsProperties.notification());
    }
}

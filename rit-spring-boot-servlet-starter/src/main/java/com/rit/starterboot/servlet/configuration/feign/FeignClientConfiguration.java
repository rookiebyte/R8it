package com.rit.starterboot.servlet.configuration.feign;

import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientConfiguration {

    @Bean
    public FeignClientFactory feignClientFactory(ApplicationContext applicationContext, LoadBalancerClient loadBalancerClient,
                                                 LoadBalancerClientFactory loadBalancerClientFactory) {
        return new FeignClientFactory(applicationContext, loadBalancerClient, loadBalancerClientFactory);
    }
}

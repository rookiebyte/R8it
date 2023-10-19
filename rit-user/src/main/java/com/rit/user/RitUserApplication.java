package com.rit.user;

import com.rit.starterboot.configuration.security.WithoutAuthenticationRequestMatcherProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication(exclude = {ErrorMvcAutoConfiguration.class})
@EnableFeignClients
public abstract class RitUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(RitUserApplication.class, args);
    }

    @Bean
    WithoutAuthenticationRequestMatcherProvider provider() {
        return new WithoutAuthenticationRequestMatcherProvider() {

            @Override
            protected List<String> pathMatchers() {
                return List.of("/hello");
            }
        };
    }
}

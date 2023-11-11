package com.rit.notification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;

@SpringBootApplication(exclude = {ErrorMvcAutoConfiguration.class})
public abstract class RitNotificationApplication {

    public static void main(String[] args) {
        SpringApplication.run(RitNotificationApplication.class, args);
    }
}

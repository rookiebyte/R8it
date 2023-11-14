package com.rit.notification;

import com.rit.starterboot.RitSpringBootApplication;
import org.springframework.boot.SpringApplication;

@RitSpringBootApplication
public abstract class RitNotificationApplication {

    public static void main(String[] args) {
        SpringApplication.run(RitNotificationApplication.class, args);
    }
}

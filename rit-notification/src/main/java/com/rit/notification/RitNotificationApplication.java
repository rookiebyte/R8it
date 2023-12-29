package com.rit.notification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public abstract class RitNotificationApplication {

    public static void main(String[] args) {
        SpringApplication.run(RitNotificationApplication.class, args);
    }
}

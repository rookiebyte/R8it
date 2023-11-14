package com.rit.user;

import com.rit.starterboot.RitSpringBootApplication;
import org.springframework.boot.SpringApplication;

@RitSpringBootApplication
public abstract class RitUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(RitUserApplication.class, args);
    }
}

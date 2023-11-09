package com.rit.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;

@SpringBootApplication(exclude = {ErrorMvcAutoConfiguration.class})
public abstract class RitUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(RitUserApplication.class, args);
    }
}

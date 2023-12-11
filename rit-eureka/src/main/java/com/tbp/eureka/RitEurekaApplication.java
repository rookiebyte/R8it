package com.tbp.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public abstract class RitEurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(RitEurekaApplication.class, args);
    }
}

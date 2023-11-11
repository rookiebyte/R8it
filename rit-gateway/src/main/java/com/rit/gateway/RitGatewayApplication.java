package com.rit.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public abstract class RitGatewayApplication {

    public static void main(String[] args) {
        System.setProperty("reactor.netty.http.server.accessLogEnabled", "true");
        SpringApplication.run(RitGatewayApplication.class, args);
    }
}

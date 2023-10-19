package com.rit.starterboot.configuration.security

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class SecurityTestEndpoint {

    public static final String SIMPLE_GET = "/testGET"

    @GetMapping(SIMPLE_GET)
    void simpleGet() {
    }
}

package com.rit.starterboot.configuration.security

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class SecurityTestEndpoint {

    public static final String SIMPLE_GET = "/testGET"
    public static final String AUTH_NEEDED_GET = "/authNeeded"

    @GetMapping(SIMPLE_GET)
    void simpleGet() {
    }

    @GetMapping(AUTH_NEEDED_GET)
    void authNeededGet() {
    }
}

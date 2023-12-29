package com.rit.starterboot.servlet.configuration.security

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class SecurityTestEndpoint {

    public static final String SIMPLE_GET = "/testGET"
    public static final String JWT_AUTH_NEEDED_GET = "/jwtAuthNeeded"
    public static final String M_TLS_AUTH_NEEDED_GET = "/mTlsAuthNeeded"

    @GetMapping(SIMPLE_GET)
    void simpleGet() {
    }

    @GetMapping(JWT_AUTH_NEEDED_GET)
    void jwtAuthNeededGet() {
    }

    @GetMapping(M_TLS_AUTH_NEEDED_GET)
    void mTlsAuthNeededGet() {
    }
}

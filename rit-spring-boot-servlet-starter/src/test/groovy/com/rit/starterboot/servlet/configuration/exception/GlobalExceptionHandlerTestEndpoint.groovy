package com.rit.starterboot.servlet.configuration.exception

import jakarta.validation.Valid
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class GlobalExceptionHandlerTestEndpoint {

    public static final String SIMPLE_GET = "/simpleGet"
    public static final String SIMPLE_GET_WITH_EXCEPTION = "/simpleGet/exception"
    public static final String SIMPLE_POST_WITH_BODY = "/simplePost"

    @GetMapping(SIMPLE_GET)
    void simpleGet() {
    }

    @PostMapping(value = SIMPLE_POST_WITH_BODY, consumes = MediaType.APPLICATION_JSON_VALUE)
    void simplePost(@Valid @RequestBody SimpleValidatedRequest request) {
    }

    @GetMapping(SIMPLE_GET_WITH_EXCEPTION)
    void simpleGetWithException() {
        throw new RuntimeException("SomeException")
    }
}

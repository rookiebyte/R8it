package com.rit.starterboot.servlet.configuration.feign

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping

interface MTLSTestClient {

    @GetMapping("/json")
    ResponseEntity<Map<String, String>> mTLSTest()
}

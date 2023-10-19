package com.rit.starterboot.configuration.swagger

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class SwaggerTestEndpoint {

    @GetMapping('/test')
    void test() {
    }
}

package com.rit.starterboot.configuration.security


import com.rit.starterboot.configuration.SpringFullContextSpecification
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.test.context.ContextConfiguration

import static SecurityTestEndpoint.SIMPLE_GET
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@ContextConfiguration(classes = [SpringTestApp, SecurityTestEndpoint])
class SecurityConfigurationSpec extends SpringFullContextSpecification {

    def 'get test endpoint, expect status is ok'() {
        when:
        def result = mockMvc.perform(get(SIMPLE_GET))
        then:
        result.andExpect(status().isOk())
    }

    def 'get auth-needed endpoint, expect status is ok'() {
        when:
        def result = mockMvc.perform(get("/authNeeded"))
        then:
        result.andExpect(status().isUnauthorized())
    }

    @SpringBootApplication
    static class SpringTestApp {

        @Bean
        WithoutAuthenticationRequestMatcherProvider withoutAuthenticationRequestMatcherProvider() {
            return new WithoutAuthenticationRequestMatcherProvider() {

                @Override
                protected List<String> pathMatchers() {
                    return List.of(SIMPLE_GET)
                }
            }
        }
    }
}

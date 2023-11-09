package com.rit.starterboot.configuration.security


import com.rit.starterboot.configuration.SpringFullContextSpecification
import com.rit.starterboot.configuration.security.jwt.JwtEncoder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpHeaders
import org.springframework.test.context.ContextConfiguration

import static SecurityTestEndpoint.SIMPLE_GET
import static SecurityTestEndpoint.AUTH_NEEDED_GET
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@ContextConfiguration(classes = [SpringTestApp, SecurityTestEndpoint])
class SecurityConfigurationSpec extends SpringFullContextSpecification {

    @Autowired
    JwtEncoder jwtEncoder

    def 'get test endpoint, expect status is ok'() {
        when:
        def result = mockMvc.perform(get(SIMPLE_GET))
        then:
        result.andExpect(status().isOk())
    }

    def 'get auth-needed endpoint, expect status is unauthorized'() {
        when:
        def result = mockMvc.perform(get(AUTH_NEEDED_GET))
        then:
        result.andExpect(status().isUnauthorized())
    }

    def 'get auth-needed endpoint, with barer token, expect status is ok'() {
        setup:
        def token = jwtEncoder.encode(new DummyJwtTemplate())
        when:
        def request = get(AUTH_NEEDED_GET)
                .header(HttpHeaders.AUTHORIZATION, "Bearer $token")
        def result = mockMvc.perform(request)
        then:
        result.andExpect(status().isOk())
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

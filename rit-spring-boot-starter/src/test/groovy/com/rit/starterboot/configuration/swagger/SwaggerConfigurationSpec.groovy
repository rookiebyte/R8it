package com.rit.starterboot.configuration.swagger

import com.fasterxml.jackson.databind.ObjectMapper
import com.rit.starterboot.RitSpringBootApplication
import com.rit.starterboot.configuration.SpringFullContextSpecification
import com.rit.starterboot.configuration.security.AuthenticationRequestMatcherProvider
import com.rit.starterboot.configuration.swagger.properties.BuildProperties
import io.swagger.v3.core.util.Json31
import io.swagger.v3.oas.models.OpenAPI
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.test.context.ContextConfiguration
import spock.lang.Shared

import static org.hamcrest.Matchers.containsString
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@ContextConfiguration(classes = [SpringTestApp, SwaggerTestEndpoint])
class SwaggerConfigurationSpec extends SpringFullContextSpecification {

    @Shared
    ObjectMapper objectMapper

    @Value('${spring.application.name}')
    String applicationName

    @Value('${server.servlet.contextPath}')
    String contextPath

    @Autowired
    BuildProperties buildProperties

    def setupSpec() {
        objectMapper = Json31.mapper()
    }

    def 'get swagger ui path, expect swagger page'() {
        when:
        def result = mockMvc.perform(get('/swagger-ui/index.html'))
        then:
        result.andExpect(status().isOk())
              .andExpect(content().string(containsString('Swagger UI')))
    }

    def 'get swagger api- docs, expect configured content'() {
        when:
        def result = mockMvc.perform(get('/v3/api-docs'))
        then:
        result.andExpect(status().isOk())
        def api = objectMapper.readValue(result.andReturn().response.getContentAsString(), OpenAPI)
        api.info.title == applicationName
        api.info.version == buildProperties.version()
        api.info.description == buildProperties.description()
        !api.security.isEmpty()
        api.paths.every { it -> it.key.contains(contextPath) }
        api.servers == null
    }


    @RitSpringBootApplication
    static class SpringTestApp {
        @Bean
        AuthenticationRequestMatcherProvider authenticationRequestMatcherProvider() {
            return new AuthenticationRequestMatcherProvider() {
                @Override
                protected List<String> permitAllPaths() {
                    return List.of("/**")
                }
            }
        }
    }
}

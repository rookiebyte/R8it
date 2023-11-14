package com.rit.starterboot.configuration.feign

import com.rit.starterboot.RitSpringBootApplication
import com.rit.starterboot.configuration.SpringFullContextSpecification
import com.rit.starterboot.configuration.feign.properties.HttpClientProperties
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.test.context.ContextConfiguration
import org.springframework.validation.annotation.Validated

@ContextConfiguration(classes = [SpringTestApp])
class FeignClientFactorySpec extends SpringFullContextSpecification {


    @Autowired
    FeignClientFactory feignClientFactory

    @Autowired
    SpringTestApp.ClientProperties clientProperties


    def "Create mTLS client, expect can make request"() {
        when:
        def client = feignClientFactory.create(MTLSTestClient, clientProperties.testClient)
        then:
        client != null
        when:
        def value = client.mTLSTest().body?.get("SSL_CLIENT_S_DN")
        then:
        value?.contains("OU=internal")
    }

    @RitSpringBootApplication
    @EnableConfigurationProperties(ClientProperties)
    static class SpringTestApp {

        @ConfigurationProperties(prefix = "http.client")
        @Validated
        static class ClientProperties {
            HttpClientProperties testClient
        }

    }
}

package com.rit.starterboot.configuration.http

import com.rit.starterboot.RitSpringBootApplication
import com.rit.starterboot.configuration.SpringFullContextSpecification
import com.rit.starterboot.infrastructure.notification.NotificationClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [SpringTestApp])
class FeignClientConfigurationSpec extends SpringFullContextSpecification {

    @Autowired
    ApplicationContext context

    def "Load spring full context, expect notification client initialized"() {
        expect:
        context.getBean(NotificationClient) != null
    }

    @RitSpringBootApplication
    static class SpringTestApp {
    }
}

package com.rit.starterboot.configuration.http

import com.rit.starterboot.RitSpringBootApplication
import com.rit.starterboot.configuration.SpringFullContextSpecification
import com.rit.starterboot.domain.notification.NotificationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [SpringTestApp])
class HttpClientConfigurationSpec extends SpringFullContextSpecification {

    @Autowired
    ApplicationContext context

    def "Load spring full context, expect notification service bean"() {
        expect:
        context.getBean(NotificationService) != null
    }

    @RitSpringBootApplication
    static class SpringTestApp {
    }
}

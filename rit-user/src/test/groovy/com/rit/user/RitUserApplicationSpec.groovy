package com.rit.user

import com.rit.spock.testcontainers.PostgresSqlContainerProvider
import com.rit.spock.testcontainers.RunWithContainer
import com.rit.user.factory.UserFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification
import spock.lang.Tag

@ActiveProfiles("test")
@SpringBootTest(classes = RitUserApplication)
@RunWithContainer(PostgresSqlContainerProvider)
@Tag(TagTestType.INTEGRATION)
class RitUserApplicationSpec extends Specification implements UserFactory {

    @Autowired
    ApplicationContext context

    def contextLoads() {
        expect:
        context != null
        context.containsBean("ritUserApplication")
    }
}

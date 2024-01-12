package com.rit.user.context.auth

import com.rit.spock.testcontainers.PostgresSqlContainerProvider
import com.rit.spock.testcontainers.RunWithContainer
import com.rit.starterboot.servlet.configuration.security.context.UserContextProvider
import com.rit.starterboot.servlet.domain.user.UserStatus
import com.rit.user.RitUserApplication
import com.rit.user.TagTestType
import com.rit.user.domain.notification.NotificationRepository
import com.rit.user.domain.user.UserRepository
import com.rit.user.factory.PropertiesFactory
import com.rit.user.factory.UserFactory
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification
import spock.lang.Tag

@AutoConfigureMockMvc
@ActiveProfiles("test")
@SpringBootTest(classes = RitUserApplication)
@RunWithContainer(PostgresSqlContainerProvider)
@Tag(TagTestType.INTEGRATION)
class AuthEndpointSpec extends Specification implements PropertiesFactory, AuthRequestFactory, UserFactory {

    @SpringBean
    private NotificationRepository notificationRepository = Mock()

    @Autowired
    MockMvc mockMvc

    @Autowired
    AuthService authService

    @Autowired
    UserRepository userRepository

    def contextLoads() {

        expect:
        mockMvc != null
    }

    def "register init, expect user created"() {
        when:
        authService.registerInit(getRegisterRequest(user, credentials()))
        then:
        var createdUser = userRepository.findUserByEmail(user.email).get()
        createdUser != null
        createdUser.email == user.email
        createdUser.username == user.username
        createdUser.userStatus == UserStatus.PENDING
        !createdUser.oneTimePasswords?.isEmpty()
        where:
        user = user()
    }
}

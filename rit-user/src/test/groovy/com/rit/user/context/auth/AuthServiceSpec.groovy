package com.rit.user.context.auth

import com.rit.robusta.util.Strings
import com.rit.starterboot.servlet.configuration.security.jwt.JwtConfiguration
import com.rit.starterboot.servlet.domain.user.UserStatus
import com.rit.user.configuration.jwt.JwtFacade
import com.rit.user.configuration.otp.properties.OtpProperties
import com.rit.user.context.auth.exception.InvalidCredentialsException
import com.rit.user.domain.notification.NotificationRepository
import com.rit.user.domain.user.OtpService
import com.rit.user.domain.user.UserOtp
import com.rit.user.domain.user.UserRepository
import com.rit.user.factory.PropertiesFactory
import com.rit.user.factory.UserFactory
import com.rit.user.infrastructure.user.InMemoryUserRepository
import com.rit.user.infrastructure.user.otp.OtpServiceConfiguration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import spock.lang.Specification

class AuthServiceSpec extends Specification implements PropertiesFactory, AuthRequestFactory, UserFactory {

    private PasswordEncoder passwordEncoder
    private UserRepository userRepository
    private OtpService otpService
    private NotificationRepository notificationRepository
    private AuthService authService

    def setup() {
        def encoder = new JwtConfiguration(jwtProperties).jwtEncoder()
        def jwtFacade = new JwtFacade(encoder, jwtProperties)
        passwordEncoder = new BCryptPasswordEncoder()
        userRepository = new InMemoryUserRepository()
        otpService = Spy(new OtpServiceConfiguration().otpService(otpProperties))
        notificationRepository = Mock()
        authService = new AuthServiceConfiguration().authService(jwtFacade, userRepository, otpService, passwordEncoder, notificationRepository)
    }

    def "login, with existing user, expect jwt"() {
        given:
        userRepository.saveUser(user(), credentials(passwordEncoder))
        when:
        var response = authService.login(getLoginRequest(credentials()))
        then:
        Strings.isNotBlank(response?.jwt())
    }

    def "login, with incorrect password, expect InvalidCredentialsException"() {
        given:
        userRepository.saveUser(user(), credentials(passwordEncoder))
        when:
        authService.login(getLoginRequest(credentials("incorrect")))
        then:
        thrown InvalidCredentialsException
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

    def "login, with enrolled user, expect jwt"() {
        given:
        UserOtp otp = null
        when:
        authService.registerInit(getRegisterRequest(user(), credentials()))
        then:
        1 * otpService.generateOtp(_) >> { args -> otp = callRealMethod(); otp }
        when:
        var results = authService.userRegisterConfirmOtp(getRegisterOtpRequest(credentials(), otp?.copyValueAsString()))
        then:
        Strings.isNotBlank(otp?.copyValueAsString())
        Strings.isNotBlank(results?.jwt())
    }
}

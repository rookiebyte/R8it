package com.rit.user.context.auth

import com.rit.starterboot.configuration.security.jwt.JwtConfiguration
import com.rit.user.configuration.jwt.JwtFacade
import com.rit.user.context.auth.exception.UserAlreadyExistsException
import com.rit.user.domain.user.OtpService
import com.rit.user.domain.user.UserOtp
import com.rit.user.factory.PropertiesFactory
import com.rit.user.factory.UserFactory
import com.rit.user.infrastructure.user.OtpServiceConfiguration
import org.junit.platform.commons.util.StringUtils
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import spock.lang.Specification

class AuthServiceSpec extends Specification implements PropertiesFactory, AuthRequestFactory, UserFactory {

    private AuthService authService
    private OtpService otpService

    def setup() {
        var encoder = new JwtConfiguration(jwtProperties).jwtEncoder()
        def jwtFacade = new JwtFacade(encoder, jwtProperties)
        otpService = Spy(new OtpServiceConfiguration().otpService())
        authService = new AuthServiceConfiguration().authService(jwtFacade, otpService, new BCryptPasswordEncoder())
    }

    def "login with registered user, expect jwt"() {
        setup:
        UserOtp otp
        when: "generate otp"
        authService.userRegisterInitWithOtp(getRegisterOtpRequest(credentials))
        then:
        1 * otpService.generateOtp(_) >> { args -> otp = callRealMethod(); otp }
        otp != null
        when:
        def registerResult = authService.register(getRegisterRequest(credentials, otp))
        then: "jwt is not blank"
        StringUtils.isNotBlank(registerResult.jwt())
        when:
        def loginResult = authService.login(getLoginRequest(credentials))
        then: "jwt is not blank"
        StringUtils.isNotBlank(loginResult.jwt())
        where:
        credentials = credentials()
    }

    def "register with already existing client, expect UserAlreadyExistsException"() {
        setup:
        UserOtp otp
        when: "generate otp"
        authService.userRegisterInitWithOtp(getRegisterOtpRequest(credentials))
        then:
        1 * otpService.generateOtp(_) >> { args -> otp = callRealMethod(); otp }
        otp != null
        when: "generate otp"
        authService.userRegisterInitWithOtp(getRegisterOtpRequest(credentials))
        then:
        thrown UserAlreadyExistsException
        where:
        credentials = credentials()
    }
}

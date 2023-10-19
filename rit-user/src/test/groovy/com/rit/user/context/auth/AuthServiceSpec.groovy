package com.rit.user.context.auth

import com.rit.starterboot.configuration.jwt.JwtKeyStore
import com.rit.user.configuration.jwt.JwtFacade
import com.rit.user.context.auth.exception.UserAlreadyExistsException
import com.rit.user.domain.user.UserRepository
import com.rit.user.factory.PropertiesFactory
import com.rit.user.factory.UserFactory
import com.rit.user.infrastructure.user.InMemoryUserRepository
import org.junit.platform.commons.util.StringUtils
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import spock.lang.Specification

class AuthServiceSpec extends Specification implements PropertiesFactory, AuthRequestFactory, UserFactory {

    private UserRepository userRepository
    private AuthService authService

    def setup() {
        def jwtFacade = new JwtFacade(new JwtKeyStore(jwtProperties), jwtProperties)
        userRepository = new InMemoryUserRepository()
        authService = new AuthService(jwtFacade, userRepository, new BCryptPasswordEncoder())
    }

    def "login with registered client, expect jwt"() {
        given: "registered user"
        authService.register(getRegisterRequest(credentials))
        when:
        def loginResult = authService.login(getLoginRequest(credentials))
        then: "jwt is not blank"
        StringUtils.isNotBlank(loginResult.jwt())
        where:
        credentials = credentials()
    }

    def "register client, expect jwt"() {
        when: "register user"
        def registerResult = authService.register(getRegisterRequest(credentials))
        then: "jwt is not blank"
        StringUtils.isNotBlank(registerResult.jwt())
        where:
        credentials = credentials()
    }

    def "register with already existing client, expect UserAlreadyExistsException"() {
        given: "existing user"
        userRepository.saveUser(user, credentials)
        when: "register user"
        authService.register(getRegisterRequest(credentials))
        then:
        thrown UserAlreadyExistsException
        where:
        credentials = credentials()
        user = user()
    }
}

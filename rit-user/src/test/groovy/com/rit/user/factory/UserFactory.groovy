package com.rit.user.factory

import com.rit.starterboot.servlet.domain.user.UserContext
import com.rit.starterboot.servlet.domain.user.UserStatus
import com.rit.user.domain.user.User
import com.rit.user.domain.user.UsersCredentials
import org.springframework.security.crypto.password.PasswordEncoder

trait UserFactory {

    private final static EMAIL = 'test@example.com'
    private final static PASSWORD = 'password'

    UserContext userContext(User user) {
        return new UserContext("test_jwt", user.id)
    }

    UsersCredentials credentials() {
        return new UsersCredentials(EMAIL, PASSWORD)
    }

    UsersCredentials credentials(PasswordEncoder passwordEncoder) {
        return new UsersCredentials(EMAIL, passwordEncoder.encode(PASSWORD))
    }

    UsersCredentials credentials(User user) {
        return new UsersCredentials(user.email, PASSWORD)
    }

    UsersCredentials credentials(String password) {
        return new UsersCredentials(EMAIL, password)
    }

    User user() {
        return userBuilder().build()
    }

    User.UserBuilder userBuilder() {
        return User.builder()
                   .email(EMAIL)
                   .username("username")
                   .userStatus(UserStatus.ACTIVE)
                   .oneTimePasswords(new HashMap<>())
    }

    String getRandomUserId() {
        return UUID.randomUUID().toString()
    }
}

package com.rit.user.factory

import com.rit.user.domain.user.User
import com.rit.user.domain.user.UsersCredentials
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

trait UserFactory {

    private final static PasswordEncoder passwordEncoder = new BCryptPasswordEncoder()


    UsersCredentials credentials() {
        return new UsersCredentials('aa@aa.pl', 'asd'.getBytes())
    }

    User user() {
        return User.builder().username("asd").email('aa@aa.pl').phoneNumber("asd").build()
    }
}

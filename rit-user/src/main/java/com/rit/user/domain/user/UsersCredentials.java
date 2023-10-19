package com.rit.user.domain.user;

import org.springframework.security.crypto.password.PasswordEncoder;

public record UsersCredentials(String email, byte[] password) {

    public boolean authenticate(PasswordEncoder passwordEncoder, LoginCredentials loginCredentials) {
        return passwordEncoder.matches(loginCredentials.getPassword(), new String(password));
    }
}

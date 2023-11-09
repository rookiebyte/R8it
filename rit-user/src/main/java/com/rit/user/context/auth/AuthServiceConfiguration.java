package com.rit.user.context.auth;

import com.rit.user.configuration.jwt.JwtFacade;
import com.rit.user.domain.user.OtpService;
import com.rit.user.domain.user.UserRepository;
import com.rit.user.infrastructure.user.InMemoryUserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AuthServiceConfiguration {

    public AuthService authService(JwtFacade jwtFacade, UserRepository userRepository,
                                   OtpService otpService, PasswordEncoder passwordEncoder) {
        return new AuthService(jwtFacade, userRepository, otpService, passwordEncoder);
    }

    @Bean
    public AuthService authService(JwtFacade jwtFacade, OtpService otpService, PasswordEncoder passwordEncoder) {
        return authService(jwtFacade, new InMemoryUserRepository(), otpService, passwordEncoder);
    }
}

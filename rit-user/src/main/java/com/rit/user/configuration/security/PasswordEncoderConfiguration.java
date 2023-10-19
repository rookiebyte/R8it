package com.rit.user.configuration.security;

import com.rit.user.configuration.security.properties.Pbkdf2Properties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

@Configuration
@EnableConfigurationProperties({Pbkdf2Properties.class})
public class PasswordEncoderConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder(Pbkdf2Properties pbkdf2Properties) {
        return new Pbkdf2PasswordEncoder(
                pbkdf2Properties.pbkdf2Secret(),
                pbkdf2Properties.saltLength(),
                pbkdf2Properties.iteration(),
                Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA512
        );
    }
}

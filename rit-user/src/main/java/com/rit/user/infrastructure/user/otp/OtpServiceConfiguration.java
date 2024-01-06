package com.rit.user.infrastructure.user.otp;

import com.rit.user.configuration.otp.properties.OtpProperties;
import com.rit.user.domain.user.OtpService;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({OtpProperties.class})
public class OtpServiceConfiguration {

    @Bean
    public OtpService otpService(OtpProperties otpProperties) {
        return new OtpFacadeService(otpProperties);
    }
}

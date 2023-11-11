package com.rit.user.infrastructure.user;

import com.rit.user.domain.user.OtpService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OtpServiceConfiguration {

    @Bean
    public OtpService otpService() {
        return new OtpFacadeService();
    }
}

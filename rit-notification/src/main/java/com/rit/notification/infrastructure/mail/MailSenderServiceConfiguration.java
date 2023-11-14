package com.rit.notification.infrastructure.mail;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MailSenderServiceConfiguration {

    @Bean
    MailSenderFacadeService mailSenderFacadeService() {
        return new MailSenderFacadeService();
    }
}

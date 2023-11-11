package com.rit.notification.infrastructure.mail;

import org.springframework.context.annotation.Bean;

public class MailSenderServiceConfiguration {

    @Bean
    MailSenderFacadeService mailSenderFacadeService() {
        return new MailSenderFacadeService();
    }
}

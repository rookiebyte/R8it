package com.rit.notification.infrastructure.email;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Configuration
public class EmailSenderServiceConfiguration {

    @Bean
    EmailSenderFacadeService mailSenderFacadeService(JavaMailSender emailSender, SpringTemplateEngine templateEngine) {
        return new EmailSenderFacadeService(emailSender, templateEngine);
    }
}

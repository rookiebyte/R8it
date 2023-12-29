package com.rit.notification.context.email;

import com.rit.notification.context.email.dto.EmailNotificationMessage;
import com.rit.notification.domain.notification.email.EmailTemplateFactory;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@AllArgsConstructor
public class EmailNotificationConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailNotificationConsumer.class);

    private final MailNotificationService mailNotificationService;
    private final EmailTemplateFactory emailTemplateFactory = new EmailTemplateFactory();

    public void consume(UUID key, EmailNotificationMessage value) {
        LOGGER.info("Received message with key[{}]", key);
        try {
            mailNotificationService.sendEmailNotification(emailTemplateFactory.createTemplate(key, value));
        } catch (Exception exception) {
            LOGGER.error("Exception occurred while processing email notification message[key={}]", key, exception);
        }
    }
}

package com.rit.starterboot.infrastructure.notification.dto;

import com.rit.starterboot.domain.notification.MailNotification;

import java.util.Map;

public record EmailNotificationRequestDto(

        String recipient,
        String subject,
        String templateName,
        Map<String, Object> bindings
) {

    public EmailNotificationRequestDto(MailNotification mailNotification) {
        this(mailNotification.getRecipient(), mailNotification.getSubject(),
                mailNotification.getTemplateName(), mailNotification.getBindings());
    }
}

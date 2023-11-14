package com.rit.notification.context.mail.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

import java.util.Map;

public record EmailNotificationRequest(

        @Email
        @NotEmpty
        String recipient,

        @NotEmpty
        String subject,

        @NotEmpty
        String templateName,

        Map<String, Object> bindings
) {
}

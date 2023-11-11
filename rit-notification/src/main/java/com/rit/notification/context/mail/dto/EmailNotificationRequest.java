package com.rit.notification.context.mail.dto;

import com.rit.starterboot.domain.notification.mail.MailNotificationTemplate;
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
) implements MailNotificationTemplate {

    @Override
    public String getTemplateName() {
        return templateName;
    }

    @Override
    public String getRecipient() {
        return recipient;
    }

    @Override
    public String getSubject() {
        return subject;
    }

    @Override
    public Map<String, Object> getBindings() {
        return bindings;
    }
}

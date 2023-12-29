package com.rit.notification.domain.notification.email;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rit.notification.context.email.dto.EmailNotificationMessage;
import com.rit.notification.exception.SendingNotificationException;
import com.rit.notification.exception.UnknownTemplateException;

import java.util.UUID;

public class EmailTemplateFactory {

    private final ObjectMapper objectMapper;

    public EmailTemplateFactory() {
        this.objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public EmailTemplate createTemplate(UUID key, EmailNotificationMessage value) {
        if ("registration/otp".equals(value.templateName())) {
            return createOtpEmailTemplate(key, value);
        }
        throw new UnknownTemplateException(value.templateName());
    }

    private EmailTemplate createOtpEmailTemplate(UUID key, EmailNotificationMessage value) {
        var bindings = treeToValue(value.bindings(), OtpEmailTemplate.OtpEmailTemplateBindings.class);
        return new OtpEmailTemplate(key, value.recipient(), value.templateName(), bindings);
    }

    private <T> T treeToValue(JsonNode node, Class<T> classType) {
        try {
            return objectMapper.treeToValue(node, classType);
        } catch (JsonProcessingException ex) {
            throw new SendingNotificationException(ex);
        }
    }
}

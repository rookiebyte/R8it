package com.rit.notification.context.email.dto;

import com.fasterxml.jackson.databind.JsonNode;

public record EmailNotificationMessage(

        String recipient,
        String templateName,
        JsonNode bindings
) {
}

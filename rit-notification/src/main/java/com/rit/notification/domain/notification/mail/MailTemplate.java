package com.rit.notification.domain.notification.mail;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
public class MailTemplate {

    private final String subject;
    private final String recipient;
    private final String templateName;
    private final Map<String, Object> bindings;
}

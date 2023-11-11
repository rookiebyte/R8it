package com.rit.starterboot.domain.notification.mail;

import java.util.Map;

public interface MailNotificationTemplate {

    String getTemplateName();

    String getRecipient();

    String getSubject();

    Map<String, Object> getBindings();
}

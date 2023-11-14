package com.rit.starterboot.domain.notification;

import java.util.Map;

public sealed interface MailNotification permits RegistrationOtpMailNotification {

    String getTemplateName();

    String getRecipient();

    String getSubject();

    Map<String, Object> getBindings();
}

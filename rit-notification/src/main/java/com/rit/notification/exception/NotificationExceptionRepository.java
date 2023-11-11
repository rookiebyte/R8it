package com.rit.notification.exception;

import com.rit.starterboot.configuration.exception.RestExceptionCode;

public final class NotificationExceptionRepository {

    public static final RestExceptionCode SENDING_NOTIFICATION_EXCEPTION = new RestExceptionCode("NOTIFICATION-001",
            "Could not send notification, contacting us provide %s");

    private NotificationExceptionRepository() {
    }
}

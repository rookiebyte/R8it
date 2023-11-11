package com.rit.notification.exception;

import com.rit.starterboot.configuration.exception.ServiceException;

public class SendingNotificationException extends ServiceException {

    public SendingNotificationException(Throwable cause) {
        super(NotificationExceptionRepository.SENDING_NOTIFICATION_EXCEPTION, cause);
    }
}

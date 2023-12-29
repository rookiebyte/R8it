package com.rit.notification.exception;

public class SendingNotificationException extends RuntimeException {

    public SendingNotificationException(String message) {
        super(message);
    }

    public SendingNotificationException(Throwable cause) {
        super("Could not send notification", cause);
    }
}

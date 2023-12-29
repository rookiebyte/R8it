package com.rit.notification.exception;

public class UnknownTemplateException extends SendingNotificationException {

    public UnknownTemplateException(String templateName) {
        super("Unknown template " + templateName + "!");
    }
}

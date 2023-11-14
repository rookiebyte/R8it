package com.rit.starterboot.domain.notification.mail;

public interface MailNotificationService {

    void sendNotification(MailNotificationTemplate mailNotificationTemplate);
}

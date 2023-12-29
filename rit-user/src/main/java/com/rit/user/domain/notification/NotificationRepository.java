package com.rit.user.domain.notification;

public interface NotificationRepository {

    void sendNotification(EmailNotification emailNotification);
}

package com.rit.starterboot.infrastructure.notification;

import com.rit.starterboot.domain.notification.MailNotification;
import com.rit.starterboot.domain.notification.NotificationService;
import com.rit.starterboot.infrastructure.notification.dto.EmailNotificationRequestDto;
import lombok.AllArgsConstructor;

@AllArgsConstructor
class NotificationFacadeService implements NotificationService {

    private final NotificationClient notificationClient;

    @Override
    public void sendNotification(MailNotification mailNotification) {
        notificationClient.sendNotification(new EmailNotificationRequestDto(mailNotification));
    }
}

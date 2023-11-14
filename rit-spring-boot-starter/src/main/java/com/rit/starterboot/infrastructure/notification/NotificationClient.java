package com.rit.starterboot.infrastructure.notification;

import com.rit.starterboot.infrastructure.notification.dto.EmailNotificationRequestDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

interface NotificationClient {

    @PostMapping("/v1/rit/notification/mail")
    void sendNotification(@RequestBody EmailNotificationRequestDto template);
}

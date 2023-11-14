package com.rit.starterboot.infrastructure.notification;

import com.rit.starterboot.infrastructure.notification.dto.EmailNotificationRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface NotificationClient {

    @PostMapping("/v1/rit/notification/mail")
    ResponseEntity<Void> sendNotification(@RequestBody EmailNotificationRequestDto template);
}

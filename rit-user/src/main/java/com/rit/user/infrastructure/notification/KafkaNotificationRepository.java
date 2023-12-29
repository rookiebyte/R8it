package com.rit.user.infrastructure.notification;

import com.rit.user.domain.notification.EmailNotification;
import com.rit.user.domain.notification.NotificationRepository;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.UUID;

@AllArgsConstructor
public class KafkaNotificationRepository implements NotificationRepository {

    KafkaTemplate<UUID, EmailNotification> emailNotificationTemplate;

    @Override
    public void sendNotification(EmailNotification emailNotification) {
        emailNotificationTemplate.sendDefault(UUID.randomUUID(), emailNotification);
    }
}

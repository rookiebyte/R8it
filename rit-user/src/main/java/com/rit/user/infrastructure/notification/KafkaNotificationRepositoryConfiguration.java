package com.rit.user.infrastructure.notification;

import com.rit.user.domain.notification.EmailNotification;
import com.rit.user.domain.notification.NotificationRepository;
import lombok.AllArgsConstructor;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.UUIDSerializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.UUID;

@Configuration(proxyBeanMethods = false)
@AllArgsConstructor
public class KafkaNotificationRepositoryConfiguration {

    private final KafkaProperties kafkaProperties;

    @Bean
    NotificationRepository notificationRepository() {
        var template = new KafkaTemplate<>(producerFactory());
        template.setDefaultTopic("rit.notification.email");
        return new KafkaNotificationRepository(template);
    }

    ProducerFactory<UUID, EmailNotification> producerFactory() {
        var properties = new HashMap<>(kafkaProperties.buildProducerProperties());
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, UUIDSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(properties);
    }
}

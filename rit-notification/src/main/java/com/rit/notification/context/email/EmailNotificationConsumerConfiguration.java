package com.rit.notification.context.email;

import com.rit.notification.context.email.dto.EmailNotificationMessage;
import lombok.AllArgsConstructor;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerde;

import java.util.UUID;

@AllArgsConstructor
@Configuration(proxyBeanMethods = false)
public class EmailNotificationConsumerConfiguration {

    private final EmailNotificationConsumer emailNotificationConsumer;

    @Bean
    public KStream<UUID, EmailNotificationMessage> mailTemplateKStream(StreamsBuilder streamsBuilder) {
        var stream = preconfiguredStream(streamsBuilder);
        stream.foreach(emailNotificationConsumer::consume);
        return stream;
    }

    KStream<UUID, EmailNotificationMessage> preconfiguredStream(StreamsBuilder streamsBuilder) {
        return streamsBuilder.stream("rit.notification.email",
                Consumed.with(new Serdes.UUIDSerde(), new JsonSerde<>(EmailNotificationMessage.class)));
    }
}

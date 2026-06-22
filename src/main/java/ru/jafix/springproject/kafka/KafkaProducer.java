package ru.jafix.springproject.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaProducer {

    private static final String TOPIC_NAME = "STATUS_DATA";

    private final KafkaTemplate<UUID, String> kafkaTemplate;

    public void publishToKafka(UUID uuid, String value) {
        log.info("Отправлено сообщение в кафку {}, топик: {}", value, TOPIC_NAME);
        kafkaTemplate.send(TOPIC_NAME, uuid, value);
    }
}

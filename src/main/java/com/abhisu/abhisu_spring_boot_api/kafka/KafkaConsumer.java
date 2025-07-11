package com.abhisu.abhisu_spring_boot_api.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaConsumer {
    /**
     * Listens to messages from the Kafka topic "test-topic".
     *
     * @param message the message received from the topic
     */
    @KafkaListener(topics = "test-topic", groupId = "my-group")
    public void listen(String message) {
        log.info("Received: {}", message);
    }
}

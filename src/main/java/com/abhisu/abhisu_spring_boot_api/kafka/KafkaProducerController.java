package com.abhisu.abhisu_spring_boot_api.kafka;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller to handle a Kafka message sending requests.
 */
@RestController
public class KafkaProducerController {

    private final KafkaProducer producer;

    public KafkaProducerController(KafkaProducer producer) {
        this.producer = producer;
    }

    /**
     * Sends a message to a Kafka topic.
     *
     * @param message the message to be sent
     * @return ResponseEntity with status
     */
    @PostMapping("/api/messages")
    public ResponseEntity<String> sendMessage(@RequestBody String message) {
        if (message == null || message.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Message must not be empty");
        }

        producer.sendMessage(message.trim());
        return ResponseEntity.ok("Message sent to Kafka");
    }
}

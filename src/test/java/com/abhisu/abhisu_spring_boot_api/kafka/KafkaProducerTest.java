package com.abhisu.abhisu_spring_boot_api.kafka;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.kafka.core.KafkaTemplate;

import static org.mockito.Mockito.*;

class KafkaProducerTest {

    private KafkaTemplate<String, String> kafkaTemplate;
    private KafkaProducer producer;

    @BeforeEach
    void setUp() {
        kafkaTemplate = mock(KafkaTemplate.class);
        producer = new KafkaProducer(kafkaTemplate);
    }

    @Test
    void testSendMessage_success() {
        String message = "Hello Kafka";
        producer.sendMessage(message);
        verify(kafkaTemplate, times(1)).send("test-topic", message);
    }

    @Test
    void testSendMessage_emptyMessage() {
        String message = "";
        producer.sendMessage(message);
        verify(kafkaTemplate).send("test-topic", message);
    }

    @Test
    void testSendMessage_nullMessage() {
        String message = null;
        producer.sendMessage(message);
        verify(kafkaTemplate).send("test-topic", message);
    }

    @Test
    void testSendMessage_kafkaThrowsException() {
        String message = "Failure Test";

        doThrow(new RuntimeException("Kafka error"))
                .when(kafkaTemplate).send("test-topic", message);

        try {
            producer.sendMessage(message);
        } catch (Exception _) {
            // Exception is expected; this ensures the call was made
            verify(kafkaTemplate).send("test-topic", message);
        }
    }
}

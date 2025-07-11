package com.abhisu.abhisu_spring_boot_api.kafka;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MessageControllerTest {

    @Test
    void testSendMessage_success() {
        KafkaProducer producer = mock(KafkaProducer.class);
        MessageController controller = new MessageController(producer);

        String message = " Hello Kafka ";
        ResponseEntity<String> response = controller.sendMessage(message);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("Message sent to Kafka", response.getBody());
        verify(producer, times(1)).sendMessage("Hello Kafka");
    }

    @Test
    void testSendMessage_empty() {
        KafkaProducer producer = mock(KafkaProducer.class);
        MessageController controller = new MessageController(producer);

        ResponseEntity<String> response = controller.sendMessage("   ");

        assertEquals(400, response.getStatusCode().value());
        assertEquals("Message must not be empty", response.getBody());
        verify(producer, never()).sendMessage(anyString());
    }

    @Test
    void testSendMessage_null() {
        KafkaProducer producer = mock(KafkaProducer.class);
        MessageController controller = new MessageController(producer);

        ResponseEntity<String> response = controller.sendMessage(null);

        assertEquals(400, response.getStatusCode().value());
        assertEquals("Message must not be empty", response.getBody());
        verify(producer, never()).sendMessage(anyString());
    }
}

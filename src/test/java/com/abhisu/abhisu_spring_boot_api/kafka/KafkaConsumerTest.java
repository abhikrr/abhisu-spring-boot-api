package com.abhisu.abhisu_spring_boot_api.kafka;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class KafkaConsumerTest {

    @ParameterizedTest
    @ValueSource(strings = {"Test message", "Kafka", "123"})
    @NullAndEmptySource
    void testListen_withVariousMessages(String input) {
        KafkaConsumer consumer = new KafkaConsumer();
        assertDoesNotThrow(() -> consumer.listen(input));
    }
}

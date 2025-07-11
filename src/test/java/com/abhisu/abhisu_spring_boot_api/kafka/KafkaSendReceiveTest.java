package com.abhisu.abhisu_spring_boot_api.kafka;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@EmbeddedKafka(partitions = 1, topics = {"test-topic"})
class KafkaSendReceiveTest {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ConsumerFactory<String, String> consumerFactory;

    @Test
    void testKafkaSendReceive() {
        kafkaTemplate.send("abhisu-topic", "Hello Kafka");

        Consumer<String, String> consumer = consumerFactory.createConsumer();
        consumer.subscribe(List.of("abhisu-topic"));

        ConsumerRecords<String, String> records = KafkaTestUtils.getRecords(consumer);
        assertFalse(records.isEmpty());
    }
}

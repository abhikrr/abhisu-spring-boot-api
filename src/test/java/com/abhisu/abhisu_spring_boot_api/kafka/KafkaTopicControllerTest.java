package com.abhisu.abhisu_spring_boot_api.kafka;

import org.apache.kafka.clients.admin.*;
import org.apache.kafka.clients.admin.TopicDescription;
import org.apache.kafka.common.KafkaFuture;
import org.apache.kafka.common.TopicPartitionInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.*;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class KafkaTopicControllerTest {

    private KafkaTopicController controller;

    @BeforeEach
    void setUp() {
        controller = new KafkaTopicController();
        controller.bootstrapServers = "localhost:9092";
    }

    @Test
    void testCreateTopic_Success() {
        String topicName = "test-topic";

        try (MockedStatic<AdminClient> mockedAdminClient = mockStatic(AdminClient.class)) {
            AdminClient mockClient = mock(AdminClient.class);
            CreateTopicsResult mockResult = mock(CreateTopicsResult.class);
            KafkaFuture<Void> mockFuture = mock(KafkaFuture.class);

            when(mockResult.all()).thenReturn(mockFuture);
            when(mockClient.createTopics(anyCollection())).thenReturn(mockResult);
            mockedAdminClient.when(() -> AdminClient.create(any(Properties.class))).thenReturn(mockClient);

            String result = controller.createTopic(topicName);
            assertTrue(result.contains("created successfully"));
        }
    }

    @Test
    void testCreateTopic_ExecutionException() throws Exception {
        String topicName = "error-topic";

        try (MockedStatic<AdminClient> mockedAdminClient = mockStatic(AdminClient.class)) {
            AdminClient mockClient = mock(AdminClient.class);
            CreateTopicsResult mockResult = mock(CreateTopicsResult.class);
            KafkaFuture<Void> mockFuture = mock(KafkaFuture.class);

            when(mockFuture.get()).thenThrow(new ExecutionException("Boom!", new Throwable("Kafka error")));
            when(mockResult.all()).thenReturn(mockFuture);
            when(mockClient.createTopics(anyCollection())).thenReturn(mockResult);
            mockedAdminClient.when(() -> AdminClient.create(any(Properties.class))).thenReturn(mockClient);

            String result = controller.createTopic(topicName);
            assertTrue(result.contains("Topic creation failed"));
        }
    }

    @Test
    void testCreateTopic_InterruptedException() throws Exception {
        String topicName = "interrupt-topic";

        try (MockedStatic<AdminClient> mockedAdminClient = mockStatic(AdminClient.class)) {
            AdminClient mockClient = mock(AdminClient.class);
            CreateTopicsResult mockResult = mock(CreateTopicsResult.class);
            KafkaFuture<Void> mockFuture = mock(KafkaFuture.class);

            // Simulate InterruptedException
            when(mockFuture.get()).thenThrow(new InterruptedException("Simulated interrupt"));
            when(mockResult.all()).thenReturn(mockFuture);
            when(mockClient.createTopics(anyCollection())).thenReturn(mockResult);
            mockedAdminClient.when(() -> AdminClient.create(any(Properties.class))).thenReturn(mockClient);

            String result = controller.createTopic(topicName);
            assertTrue(result.contains("InterruptedException"));
        }
    }


    @Test
    void testGetKafkaTopics_Success() throws Exception {
        try (MockedStatic<AdminClient> mockedAdminClient = mockStatic(AdminClient.class)) {
            AdminClient mockClient = mock(AdminClient.class);
            ListTopicsResult mockResult = mock(ListTopicsResult.class);
            KafkaFuture<Set<String>> mockFuture = mock(KafkaFuture.class);

            when(mockFuture.get()).thenReturn(Set.of("topic-1", "topic-2"));
            when(mockResult.names()).thenReturn(mockFuture);
            when(mockClient.listTopics()).thenReturn(mockResult);
            mockedAdminClient.when(() -> AdminClient.create(any(Properties.class))).thenReturn(mockClient);

            String result = controller.getKafkaTopics();
            assertTrue(result.contains("Available topics"));
        }
    }

    @Test
    void testGetKafkaTopics_ExecutionException() throws Exception {
        try (MockedStatic<AdminClient> mockedAdminClient = mockStatic(AdminClient.class)) {
            AdminClient mockClient = mock(AdminClient.class);
            ListTopicsResult mockResult = mock(ListTopicsResult.class);
            KafkaFuture<Set<String>> mockFuture = mock(KafkaFuture.class);

            // Simulate ExecutionException
            when(mockFuture.get()).thenThrow(new ExecutionException("Simulated", new Throwable("Simulated error")));
            when(mockResult.names()).thenReturn(mockFuture);
            when(mockClient.listTopics()).thenReturn(mockResult);
            mockedAdminClient.when(() -> AdminClient.create(any(Properties.class))).thenReturn(mockClient);

            String result = controller.getKafkaTopics();
            assertTrue(result.contains("Error fetching topics"));
        }
    }

    @Test
    void testGetKafkaTopics_InterruptedException() throws Exception {
        try (MockedStatic<AdminClient> mockedAdminClient = mockStatic(AdminClient.class)) {
            AdminClient mockClient = mock(AdminClient.class);
            ListTopicsResult mockResult = mock(ListTopicsResult.class);
            KafkaFuture<Set<String>> mockFuture = mock(KafkaFuture.class);

            // Simulate InterruptedException
            when(mockFuture.get()).thenThrow(new InterruptedException("Simulated interrupt"));
            when(mockResult.names()).thenReturn(mockFuture);
            when(mockClient.listTopics()).thenReturn(mockResult);
            mockedAdminClient.when(() -> AdminClient.create(any(Properties.class))).thenReturn(mockClient);

            String result = controller.getKafkaTopics();
            assertTrue(result.contains("InterruptedException")); // it returns e.toString()
        }
    }



    @Test
    void testDescribeTopic_Success() throws Exception {
        String topicName = "describe-topic";

        try (MockedStatic<AdminClient> mockedAdminClient = mockStatic(AdminClient.class)) {
            AdminClient mockClient = mock(AdminClient.class);
            DescribeTopicsResult mockResult = mock(DescribeTopicsResult.class);
            KafkaFuture<TopicDescription> mockFuture = mock(KafkaFuture.class);

            TopicPartitionInfo partitionInfo = mock(TopicPartitionInfo.class);
            when(partitionInfo.partition()).thenReturn(0);
            when(partitionInfo.replicas()).thenReturn(List.of());
            when(partitionInfo.isr()).thenReturn(List.of());
            when(partitionInfo.leader()).thenReturn(null);

            TopicDescription description = mock(TopicDescription.class);
            when(description.name()).thenReturn(topicName);
            when(description.topicId()).thenReturn(null);
            when(description.partitions()).thenReturn(List.of(partitionInfo));

            when(mockFuture.get()).thenReturn(description);
            when(mockResult.topicNameValues()).thenReturn(Map.of(topicName, mockFuture));
            when(mockClient.describeTopics(anyCollection())).thenReturn(mockResult);
            mockedAdminClient.when(() -> AdminClient.create(any(Properties.class))).thenReturn(mockClient);

            String result = controller.getKafkaTopic(topicName);
            assertTrue(result.contains("Topic Name"));
        }
    }

    @Test
    void testDescribeTopic_ExecutionException() throws Exception {
        String topicName = "desc-error-topic";

        try (MockedStatic<AdminClient> mockedAdminClient = mockStatic(AdminClient.class)) {
            AdminClient mockClient = mock(AdminClient.class);
            DescribeTopicsResult mockResult = mock(DescribeTopicsResult.class);
            KafkaFuture<TopicDescription> mockFuture = mock(KafkaFuture.class);

            // Simulate ExecutionException
            when(mockFuture.get()).thenThrow(new ExecutionException("Simulated", new Throwable("Simulated error")));
            when(mockResult.topicNameValues()).thenReturn(Map.of(topicName, mockFuture));
            when(mockClient.describeTopics(anyCollection())).thenReturn(mockResult);
            mockedAdminClient.when(() -> AdminClient.create(any(Properties.class))).thenReturn(mockClient);

            String result = controller.getKafkaTopic(topicName);
            assertTrue(result.contains("Failed to describe topic"));
        }
    }

    @Test
    void testDescribeTopic_InterruptedException() throws Exception {
        String topicName = "desc-interrupt-topic";

        try (MockedStatic<AdminClient> mockedAdminClient = mockStatic(AdminClient.class)) {
            AdminClient mockClient = mock(AdminClient.class);
            DescribeTopicsResult mockResult = mock(DescribeTopicsResult.class);
            KafkaFuture<TopicDescription> mockFuture = mock(KafkaFuture.class);

            // Simulate InterruptedException
            when(mockFuture.get()).thenThrow(new InterruptedException("Simulated interrupt"));
            when(mockResult.topicNameValues()).thenReturn(Map.of(topicName, mockFuture));
            when(mockClient.describeTopics(anyCollection())).thenReturn(mockResult);
            mockedAdminClient.when(() -> AdminClient.create(any(Properties.class))).thenReturn(mockClient);

            String result = controller.getKafkaTopic(topicName);
            assertTrue(result.contains("InterruptedException"));
        }
    }

    @Test
    void testDeleteTopic_Success() {
        String topic = "delete-topic";

        try (MockedStatic<AdminClient> mockedAdminClient = Mockito.mockStatic(AdminClient.class)) {
            AdminClient mockClient = mock(AdminClient.class);
            DeleteTopicsResult mockResult = mock(DeleteTopicsResult.class);
            KafkaFuture<Void> mockFuture = mock(KafkaFuture.class);

            when(mockResult.all()).thenReturn(mockFuture);
            when(mockClient.deleteTopics(Collections.singleton(topic))).thenReturn(mockResult);
            mockedAdminClient.when(() -> AdminClient.create(any(Properties.class))).thenReturn(mockClient);

            String result = controller.deleteTopic(topic);
            assertTrue(result.contains("deleted successfully"));
        }
    }

    @Test
    void testDeleteTopic_ExecutionException() throws Exception {
        String topic = "bad-topic";

        try (MockedStatic<AdminClient> mockedAdminClient = Mockito.mockStatic(AdminClient.class)) {
            AdminClient mockClient = mock(AdminClient.class);
            DeleteTopicsResult mockResult = mock(DeleteTopicsResult.class);
            KafkaFuture<Void> mockFuture = mock(KafkaFuture.class);

            when(mockFuture.get()).thenThrow(new ExecutionException("Boom", new Throwable("Kafka failed")));
            when(mockResult.all()).thenReturn(mockFuture);
            when(mockClient.deleteTopics(Collections.singleton(topic))).thenReturn(mockResult);
            mockedAdminClient.when(() -> AdminClient.create(any(Properties.class))).thenReturn(mockClient);

            String result = controller.deleteTopic(topic);
            assertTrue(result.contains("Failed to delete topic"));
        }
    }

    @Test
    void testDeleteTopic_InterruptedException() throws Exception {
        String topic = "interrupt-topic";

        try (MockedStatic<AdminClient> mockedAdminClient = Mockito.mockStatic(AdminClient.class)) {
            AdminClient mockClient = mock(AdminClient.class);
            DeleteTopicsResult mockResult = mock(DeleteTopicsResult.class);
            KafkaFuture<Void> mockFuture = mock(KafkaFuture.class);

            when(mockFuture.get()).thenThrow(new InterruptedException("Thread interrupted"));
            when(mockResult.all()).thenReturn(mockFuture);
            when(mockClient.deleteTopics(Collections.singleton(topic))).thenReturn(mockResult);
            mockedAdminClient.when(() -> AdminClient.create(any(Properties.class))).thenReturn(mockClient);

            String result = controller.deleteTopic(topic);
            assertTrue(result.contains("InterruptedException"));
        }
    }
}

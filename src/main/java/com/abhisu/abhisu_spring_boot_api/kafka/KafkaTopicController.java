package com.abhisu.abhisu_spring_boot_api.kafka;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

@RestController(value = "/api/kafka/topic")
public class KafkaTopicController {

    @Value("${spring.kafka.bootstrap-servers}")
    String bootstrapServers;

    int partitions = 3;

    short replicationFactor = 1;

    @GetMapping("/create-topic/{topicName}")
    public String createTopic(@PathVariable String topicName) {

        Properties config = new Properties();
        config.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);

        try (AdminClient adminClient = AdminClient.create(config)) {

            NewTopic newTopic = new NewTopic(topicName, partitions, replicationFactor);

            adminClient.createTopics(Collections.singleton(newTopic)).all().get();

            System.out.println("Topic '" + topicName + "' created successfully!");
        } catch (ExecutionException e) {
            System.err.println("Topic creation failed: " + e.getCause().getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Kafka topic created successfully!";
    }

    @GetMapping("/get-topic/all")
    public String  getKafkaTopics() {
        Properties config = new Properties();
        config.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);

        try (AdminClient adminClient = AdminClient.create(config)) {
            return "Available topics: " + adminClient.listTopics().names().get();
        } catch (ExecutionException e) {
            return "Error fetching topics: " + e.getCause().getMessage();
        } catch (Exception e) {
            e.printStackTrace();
            return "An error occurred while fetching topics.";
        }
    }
}

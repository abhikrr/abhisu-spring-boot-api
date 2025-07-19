package com.abhisu.abhisu_spring_boot_api.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.*;
import org.apache.kafka.common.KafkaFuture;
import org.apache.kafka.common.TopicPartitionInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

@Slf4j
@RestController
public class KafkaTopicController {

    @Value("${spring.kafka.bootstrap-servers}")
    String bootstrapServers;

    int partition = 3;

    short replicationFactor = 1;

    //$ bin/kafka-topics.sh --create --topic quickstart-events --bootstrap-server localhost:9092
    @GetMapping("/api/create-topic/{topicName}")
    public String createTopic(@PathVariable String topicName) {

        Properties config = new Properties();
        config.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);

        try (AdminClient adminClient = AdminClient.create(config)) {

            NewTopic newTopic = new NewTopic(topicName, partition, replicationFactor);

            adminClient.createTopics(Collections.singleton(newTopic)).all().get();

            return ("Topic " + topicName + " created successfully!" );

        } catch (ExecutionException e) {
            return "Topic creation failed: " + e.getCause().getMessage();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return  e.toString();
        }
    }

    //$ bin/kafka-topics.sh --list --bootstrap-server localhost:9092
    @GetMapping("/api/list-topic/all")
    public String  getKafkaTopics() {
        Properties config = new Properties();
        config.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);

        try (AdminClient adminClient = AdminClient.create(config)) {
            return "Available topics: " + adminClient.listTopics().names().get();
        } catch (ExecutionException e) {
            return "Error fetching topics: " + e.getCause().getMessage();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return  e.toString();
        }
    }

    //$ bin/kafka-topics.sh --describe --topic quickstart-events --bootstrap-server localhost:9092
    @GetMapping("/api/get-topic/{topicName}")
    public String getKafkaTopic(@PathVariable String topicName) {

        Properties config = new Properties();
        config.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);

        try (AdminClient adminClient = AdminClient.create(config)) {
            DescribeTopicsResult result = adminClient.describeTopics(Collections.singleton(topicName));
            Map<String, KafkaFuture<TopicDescription>> descriptionMap = result.topicNameValues();

            TopicDescription description = descriptionMap.get(topicName).get();

            List<TopicPartitionInfo> partitions = description.partitions();

            for (TopicPartitionInfo partitionInfo : partitions) {
                log.info("--------------------------------------------------");
                log.info("Partition: {}", partitionInfo.partition());
                log.info("Leader: {}", partitionInfo.leader());
                log.info("Replicas: {}", partitionInfo.replicas());
                log.info("ISR: {}", partitionInfo.isr());
            }
            return "Topic Id: " + description.topicId() +
                    " Topic Name: " + description.name() +
                    " Partition Count: " + description.partitions().size() +
                    " Replication Factor: " + description.partitions().get(0).replicas().size();

        } catch (ExecutionException e) {
            return "Failed to describe topic: " + e.getCause().getMessage();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return e.toString();
        }
    }

    @DeleteMapping("/api/delete-topic/{topicName}")
    public String deleteTopic(@PathVariable String topicName) {

        Properties config = new Properties();
        config.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);

        try (AdminClient adminClient = AdminClient.create(config)) {
            adminClient.deleteTopics(Collections.singleton(topicName)).all().get();
            return "Topic " + topicName + " deleted successfully!";
        } catch (ExecutionException e) {
            return "Failed to delete topic: " + e.getCause().getMessage();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return e.toString();
        }
    }

}

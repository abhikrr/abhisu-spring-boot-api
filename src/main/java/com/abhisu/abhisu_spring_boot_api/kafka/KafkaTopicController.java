package com.abhisu.abhisu_spring_boot_api.kafka;

import org.apache.kafka.clients.admin.*;
import org.apache.kafka.common.TopicPartitionInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

@RestController(value = "/api/kafka/topic")
public class KafkaTopicController {

    @Value("${spring.kafka.bootstrap-servers}")
    String bootstrapServers;

    int partitions = 3;

    short replicationFactor = 1;

    //$ bin/kafka-topics.sh --create --topic quickstart-events --bootstrap-server localhost:9092
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

    //$ bin/kafka-topics.sh --describe --topic quickstart-events --bootstrap-server localhost:9092
    @GetMapping("/describe/{topicName}")
    public String getKafkaTopic(@PathVariable String topicName) {

        Properties config = new Properties();
        config.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);

        try (AdminClient adminClient = AdminClient.create(config)) {
            DescribeTopicsResult result = adminClient.describeTopics(Collections.singleton(topicName));
            Map<String, TopicDescription> descriptionMap = result.all().get();

            TopicDescription description = descriptionMap.get(topicName);

            System.out.println("📄 Topic: " + description.name());
            System.out.println("Partitions: " + description.partitions().size());

            List<TopicPartitionInfo> partitions = description.partitions();
            for (TopicPartitionInfo partitionInfo : partitions) {
                System.out.println("--------------------------------------------------");
                System.out.println("Partition: " + partitionInfo.partition());
                System.out.println("Leader: " + partitionInfo.leader());
                System.out.println("Replicas: " + partitionInfo.replicas());
                System.out.println("ISR: " + partitionInfo.isr());
            }
            return "Topic Id: " + description.topicId() +
                    " Topic Name: " + description.partitions().size() +
                    " Partition Count: " + description.partitions().size() +
                    " Replication Factor: " + description.partitions().get(0).replicas().size();

        } catch (ExecutionException e) {
            System.err.println("Failed to describe topic: " + e.getCause().getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

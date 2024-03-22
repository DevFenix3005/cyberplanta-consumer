package com.rebirth.cyberplanta.consumer.listeners;

import java.util.concurrent.CountDownLatch;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Component;

import com.rebirth.cyberplanta.commons.domain.entities.EnvironmentalMeasurement;
import com.rebirth.cyberplanta.consumer.domain.repositories.EnvironmentRepository;
import com.rebirth.cyberplanta.utilities.mappers.EnvironmentalMeasureMapper;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MyKafkaListenerComponent {

    private final EnvironmentalMeasureMapper environmentalMeasureMapper;

    private final EnvironmentRepository environmentRepository;

    private final CountDownLatch latch;

    public MyKafkaListenerComponent(EnvironmentalMeasureMapper environmentalMeasureMapper, EnvironmentRepository environmentRepository) {
        this.environmentalMeasureMapper = environmentalMeasureMapper;
        this.environmentRepository = environmentRepository;
        this.latch = new CountDownLatch(1);
    }

    @KafkaListener(
            topics = "${spring.kafka.server00.consumer.properties.envmess-topic}",
            groupId = "${spring.kafka.server00.consumer.group-id}",
            containerFactory = "kafkaListenerContainerFactory",
            topicPartitions = @TopicPartition(topic = "${spring.kafka.server00.consumer.properties.envmess-topic}", partitions = "0")
    )
    public void listenGroupEnvMess(ConsumerRecord<String, byte[]> environmentConsumerRecord) {

        latch.countDown();

        EnvironmentalMeasurement environmentalMeasurement =
                this.environmentalMeasureMapper.kafkaConsumerRecordToMongoDocument(environmentConsumerRecord);
        String topic = environmentConsumerRecord.topic();
        String key = environmentConsumerRecord.key();

        log.info("Offset: {}", environmentConsumerRecord.offset());
        log.info("Key: {}", key);
        log.info("Headers: {}", environmentConsumerRecord.headers());
        log.info("Partition: {}", environmentConsumerRecord.partition());
        log.info("Topic: {}", topic);
        log.info("Timestamp: {}", environmentConsumerRecord.timestamp());

        log.info("Ready to save!!");
        this.environmentRepository.save(environmentalMeasurement);

    }

}

package com.rebirth.cyberplanta.consumer.configurations;

import java.util.Map;

import org.apache.avro.Schema;
import org.apache.avro.io.DatumReader;
import org.apache.avro.specific.SpecificDatumReader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.web.client.RestTemplate;

import com.rebirth.cyberplanta.commons.avro.model.EnvironmentalMeasurementAvroDTO;
import com.rebirth.cyberplanta.commons.core.KafkaServer00Properties;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableKafka
@Slf4j
public class KafkaConsumerConfiguration {

    private final RestTemplate restTemplate;

    public KafkaConsumerConfiguration(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Bean
    public ConsumerFactory<String, Object> consumerFactory(KafkaServer00Properties kafkaServer00Properties) {
        Map<String, Object> props = kafkaServer00Properties.buildConsumerProperties(null);
        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory(
            ConsumerFactory<String, Object> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }

    @Bean(name = "environmentalMeasurementAvroDTOSchema")
    public Schema schemaFromSchemaRegister(KafkaServer00Properties kafkaServer00Properties) {
        Map<String, String> properties = kafkaServer00Properties.getConsumer().getProperties();

        String url = properties.get("schema-register-server");
        String subject = properties.get("schema-subject");
        String version = properties.get("schema-version");
        String formattedUrl = "%s/subjects/%s/versions/%s/schema".formatted(url, subject, version);
        log.info("My schema url: {}", formattedUrl);
        ResponseEntity<String> response = this.restTemplate.getForEntity(formattedUrl, String.class);
        return new Schema.Parser().parse(response.getBody());
    }

    @Bean
    public DatumReader<EnvironmentalMeasurementAvroDTO> environmentalMeasurementAvroDTODatumReader(
            @Qualifier("environmentalMeasurementAvroDTOSchema") Schema schemaFromSchemaRegister
    ) {
        Schema currentSchema = EnvironmentalMeasurementAvroDTO.getClassSchema();
        return new SpecificDatumReader<>(currentSchema, schemaFromSchemaRegister);
    }

}

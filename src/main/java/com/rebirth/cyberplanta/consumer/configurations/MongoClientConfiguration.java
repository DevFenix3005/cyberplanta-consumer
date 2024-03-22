package com.rebirth.cyberplanta.consumer.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@Configuration
@EntityScan({
        "com.rebirth.mykafka.commons.domain.entities"
})
@EnableMongoRepositories({
        "com.rebirth.mykafka.consumer.domain.repositories"
})
@EnableMongoAuditing(auditorAwareRef = "mongoAuditorAware")
public class MongoClientConfiguration {

    @Value("${spring.data.mongodb.uri}")
    private String mongodbUri;

    @Value("${spring.data.mongodb.database}")
    private String mongodbName;

    @Bean
    public MongoClient mongo() {
        ConnectionString connectionString = new ConnectionString(mongodbUri);
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();

        return MongoClients.create(mongoClientSettings);
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongo(), mongodbName);
    }

}

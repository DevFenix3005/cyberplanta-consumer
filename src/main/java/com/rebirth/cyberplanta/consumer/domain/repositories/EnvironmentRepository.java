package com.rebirth.cyberplanta.consumer.domain.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.rebirth.cyberplanta.commons.domain.entities.EnvironmentalMeasurement;

@RepositoryRestResource(path = "environment", collectionResourceRel = "environment")
public interface EnvironmentRepository extends MongoRepository<EnvironmentalMeasurement, String> {
}

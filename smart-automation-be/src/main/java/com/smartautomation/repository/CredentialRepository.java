package com.smartautomation.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.smartautomation.model.Credential;

@Repository
public interface CredentialRepository extends MongoRepository<Credential, String> {
    
    List<Credential> findByConnectorType(String connectorType);
}

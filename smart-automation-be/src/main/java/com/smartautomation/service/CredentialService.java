package com.smartautomation.service;

import java.util.List;

import com.smartautomation.model.Credential;

public interface CredentialService {
    
    Credential save(Credential credential);
    Credential getById(String id);
    String getDecryptedData(String id);
    List<Credential> getAll();
    List<Credential> getConnectorType(String connectorType);
    void delete(String id);
    Credential update(String id, Credential credential);
}

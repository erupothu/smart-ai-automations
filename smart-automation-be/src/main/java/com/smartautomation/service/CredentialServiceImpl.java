package com.smartautomation.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.smartautomation.model.Credential;
import com.smartautomation.model.Node;
import com.smartautomation.model.WorkflowDefinition;
import com.smartautomation.repository.CredentialRepository;
import com.smartautomation.repository.WorkflowDefinitionRepository;


@Service
public class CredentialServiceImpl implements CredentialService {

    private final Logger log = LoggerFactory.getLogger(CredentialServiceImpl.class);

    private final CredentialRepository credentialRepository;
    private final WorkflowDefinitionRepository workflowDefinitionRepository;
    private final EncryptionService encryptionService;

    

    public CredentialServiceImpl(CredentialRepository credentialRepository,
            WorkflowDefinitionRepository workflowDefinitionRepository, EncryptionService encryptionService) {
        this.credentialRepository = credentialRepository;
        this.workflowDefinitionRepository = workflowDefinitionRepository;
        this.encryptionService = encryptionService;
    }

    @Override
    public Credential save(Credential credential) {
        credential.setId(UUID.randomUUID().toString());
        Instant now = Instant.now();
        credential.setCreatedAt(now);
        credential.setUpdatedAt(now);

        if(credential.getEncriptedData() != null) {
            credential.setEncriptedData(encryptionService.encrypt(credential.getEncriptedData()));
        }

        return credentialRepository.save(credential);
    }

    @Override
    public Credential getById(String id) {
        return credentialRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("cedentials not found"));
    }

    @Override
    public String getDecryptedData(String id) {
        Credential credential = getById(id);
        if(credential.getEncriptedData() == null) {
            return null;
        }
        return encryptionService.decrypt(credential.getEncriptedData());
    }

    @Override
    public List<Credential> getAll() {
        return credentialRepository.findAll();
    }

    @Override
    public List<Credential> getConnectorType(String connectorType) {
        return credentialRepository.findByConnectorType(connectorType);
    }

    @Override
    public void delete(String id) {
        Credential credential = getById(id);

        List<WorkflowDefinition> invalidatedWorkflows = cascadeInvalidateWorkflows(id);

        if(!invalidatedWorkflows.isEmpty()) {
            invalidatedWorkflows.stream().map(w -> w.getId() + " (" + w.getName() + ")").toList();
        }
        credentialRepository.deleteById(id);
    }

    @Override
    public Credential update(String id, Credential credential) {
        Credential existing = getById(id);

        existing.setName(credential.getName());
        existing.setConnectorType(credential.getConnectorType());
        existing.setAuthtype(credential.getAuthtype());
        existing.setUpdatedAt(credential.getUpdatedAt());

        if(credential.getEncriptedData() != null) {
            existing.setEncriptedData(encryptionService.encrypt(credential.getEncriptedData()));
        }

        return credentialRepository.save(existing);
    }

    private List<WorkflowDefinition> cascadeInvalidateWorkflows(String credentialId) {
        List<WorkflowDefinition> allWorkflows = workflowDefinitionRepository.findAll();
        List<WorkflowDefinition> invalidated = new ArrayList<>();

        for(WorkflowDefinition workflow: allWorkflows) {
            boolean references = false;
            if(workflow.getNodes() != null) {
                for(Node node: workflow.getNodes()) {
                    if(credentialId.equals(node.getCredentialId())) {
                        references = true;
                        break;
                    }
                }
            }
            if(references && workflow.isEnabled()) {
                workflow.setEnabled(false);
                workflow.setUpdatedAt(Instant.now());
                workflowDefinitionRepository.save(workflow);
                invalidated.add(workflow);
            }
        }
        return invalidated;
    }
    
}

package com.smartautomation.service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.smartautomation.model.Credential;
import com.smartautomation.model.RunStatus;
import com.smartautomation.model.TransientRunState;
import com.smartautomation.model.WorkflowDefinition;
import com.smartautomation.model.WorkflowRun;
import com.smartautomation.repository.CredentialRepository;
import com.smartautomation.repository.TransientStateRepository;
import com.smartautomation.repository.WorkflowDefinitionRepository;
import com.smartautomation.repository.WorkflowRunRepository;

@Service
public class PersistenceServiceImpl implements PersistenceService {

    private final WorkflowDefinitionRepository workflowDefinitionRepository;
    private final WorkflowRunRepository workflowRunRepository;
    private final CredentialRepository credentialRepository;
    private final TransientStateRepository  transientStateRepository;

    

    public PersistenceServiceImpl(WorkflowDefinitionRepository workflowDefinitionRepository,
            WorkflowRunRepository workflowRunRepository, CredentialRepository credentialRepository,
            TransientStateRepository transientStateRepository) {
        this.workflowDefinitionRepository = workflowDefinitionRepository;
        this.workflowRunRepository = workflowRunRepository;
        this.credentialRepository = credentialRepository;
        this.transientStateRepository = transientStateRepository;
    }

    @Override
    public WorkflowDefinition saveWorkflow(WorkflowDefinition definition) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saveWorkflow'");
    }

    @Override
    public WorkflowDefinition updateWorkflow(String id, WorkflowDefinition definition) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateWorkflow'");
    }

    @Override
    public Optional<WorkflowDefinition> getWorkflowById(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getWorkflowById'");
    }

    @Override
    public List<WorkflowDefinition> getAllWorkflows() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllWorkflows'");
    }

    @Override
    public void deleteWorkflow(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteWorkflow'");
    }

    @Override
    public WorkflowRun saveRun(WorkflowRun run) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saveRun'");
    }

    @Override
    public Optional<WorkflowRun> getRunById(String runId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getRunById'");
    }

    @Override
    public List<WorkflowRun> getRunsByWorkflwoId(String workflowId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getRunsByWorkflwoId'");
    }

    @Override
    public List<WorkflowRun> filterRuns(String workflowId, RunStatus status, Instant from, Instant to) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'filterRuns'");
    }

    @Override
    public Credential saveCredential(Credential credential) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saveCredential'");
    }

    @Override
    public Optional<Credential> getCredentialById(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCredentialById'");
    }

    @Override
    public List<Credential> getAllCredentials() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllCredentials'");
    }

    @Override
    public List<Credential> getCredentialsByConnectorType(String connectorType) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCredentialsByConnectorType'");
    }

    @Override
    public void delteCredentials(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delteCredentials'");
    }

    @Override
    public TransientRunState savTransientRunState(TransientRunState state) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'savTransientRunState'");
    }

    @Override
    public Optional<TransientRunState> getTransientStateByRunId(String runId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getTransientStateByRunId'");
    }

    @Override
    public void delteTransientState(String runid) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delteTransientState'");
    }
    
}

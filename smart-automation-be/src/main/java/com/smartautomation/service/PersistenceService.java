package com.smartautomation.service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import com.smartautomation.model.Credential;
import com.smartautomation.model.RunStatus;
import com.smartautomation.model.TransientRunState;
import com.smartautomation.model.WorkflowDefinition;
import com.smartautomation.model.WorkflowRun;

public interface PersistenceService {
    
    WorkflowDefinition saveWorkflow(WorkflowDefinition definition);
    WorkflowDefinition updateWorkflow(String id, WorkflowDefinition definition);
    Optional<WorkflowDefinition> getWorkflowById(String id);
    List<WorkflowDefinition> getAllWorkflows();
    void deleteWorkflow(String id);

    WorkflowRun saveRun(WorkflowRun run);
    Optional<WorkflowRun> getRunById(String runId);
    List<WorkflowRun> getRunsByWorkflwoId(String workflowId);
    List<WorkflowRun> filterRuns(String workflowId, RunStatus status, Instant from, Instant to);


    Credential saveCredential(Credential credential);
    Optional<Credential> getCredentialById(String id);
    List<Credential> getAllCredentials();
    List<Credential> getCredentialsByConnectorType(String connectorType);
    void delteCredentials(String id);

    TransientRunState savTransientRunState(TransientRunState state);
    Optional<TransientRunState> getTransientStateByRunId(String runId);
    void delteTransientState(String runid);
}

package com.smartautomation.service;

import java.time.Instant;
import java.util.List;
import java.util.Map;

import com.smartautomation.model.RunStatus;
import com.smartautomation.model.WorkflowRun;

public interface WorkflowExecutionService {
    
    WorkflowRun trigger(String workflowId);
    WorkflowRun getRunById(String runId);
    List<WorkflowRun> getRunByWorkflow(String workflowId, RunStatus status, Instant from, Instant to);
    Map<String, Object> executeSingleNode(Map<String, Object> request);
    
}

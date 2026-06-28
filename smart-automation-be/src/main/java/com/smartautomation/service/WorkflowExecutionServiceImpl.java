package com.smartautomation.service;

import java.time.Instant;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.smartautomation.model.RunStatus;
import com.smartautomation.model.WorkflowRun;

@Service
public class WorkflowExecutionServiceImpl implements WorkflowExecutionService {

    @Override
    public WorkflowRun trigger(String workflowId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'trigger'");
    }

    @Override
    public WorkflowRun getRunById(String runId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getRunById'");
    }

    @Override
    public List<WorkflowRun> getRunByWorkflow(String workflowId, RunStatus status, Instant from, Instant to) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getRunByWorkflow'");
    }

    @Override
    public Map<String, Object> executeSingleNode(Map<String, Object> request) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'executeSingleNode'");
    }
    
}

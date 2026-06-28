package com.smartautomation.model;

import java.time.Instant;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@Table(name = "transient_run_state")
public class TransientRunState {
    
    @Id
    private String runId;
    private String workflowId;
    private String currentNodeId;
    private String status;

    @Lob
    private String nodeDataCache;
    private Instant lastUpdated;

    public TransientRunState() {}

    public String getRunId() {
        return runId;
    }

    public void setRunId(String runId) {
        this.runId = runId;
    }

    public String getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId;
    }

    public String getCurrentNodeId() {
        return currentNodeId;
    }

    public void setCurrentNodeId(String currentNodeId) {
        this.currentNodeId = currentNodeId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNodeDataCache() {
        return nodeDataCache;
    }

    public void setNodeDataCache(String nodeDataCache) {
        this.nodeDataCache = nodeDataCache;
    }

    public Instant getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Instant lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    

}

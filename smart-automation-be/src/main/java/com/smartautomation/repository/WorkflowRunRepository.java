package com.smartautomation.repository;

import java.time.Instant;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.smartautomation.model.RunStatus;
import com.smartautomation.model.WorkflowRun;

@Repository
public interface WorkflowRunRepository extends MongoRepository<WorkflowRun, String> {
    
    List<WorkflowRun> findByWorkflowId(String workflowId);
    List<WorkflowRun> findByWorkflowIdAndStatus(String workflowId, RunStatus status);
    List<WorkflowRun> findByWorkflowIdAndStartedAtBetween(String workflowId, Instant from, Instant to);

    @Query("{ 'workflowId': ?0, 'status': ?1, 'startedAt': { $gte: ?2, $lte: ?3} }")
    List<WorkflowRun> findByWorkflowIdAndstatusAndStartedAtBetween(String workflowId, RunStatus status, Instant from, Instant to);
    
    List<WorkflowRun> findByStatus(RunStatus status);
}
